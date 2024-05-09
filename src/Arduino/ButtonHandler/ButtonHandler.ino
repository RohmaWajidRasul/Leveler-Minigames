#include "Arduino.h"
#include "rpcWiFi.h"
#include "PubSubClient.h"

WiFiClient wifiClient;
PubSubClient client(wifiClient);

const char* ssid = "";
const char* password =  "";

const int yellowButtonPin = 1;   // Yellow button pin (terminal right)
const int redButtonPin = 5;      // Red button pin    (battery port 5) 
const int blueButtonPin = 8;     // Blue button pin   (battery port 2)

// Variables will change:
int yellowButtonState;            // Yellow button state
int redButtonState;               // Red button state
int blueButtonState;              // Blue button state
int lastYellowButtonState = HIGH; // Previous yellow button state
int lastRedButtonState = HIGH;    // Previous red button state
int lastBlueButtonState = HIGH;   // Previous blue button state

unsigned long lastDebounceTime = 0;  // Last time the output pin was toggled
unsigned long debounceDelay = 50;    // Debounce time; increase if output flickers

void setup() {
  Serial.begin(9600);

  // Connect to WiFi
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    WiFi.begin(ssid, password);
  }

  // Set up MQTT client
  client.setServer("test.mosquitto.org", 1883);

  pinMode(yellowButtonPin, INPUT);
  pinMode(redButtonPin, INPUT);
  pinMode(blueButtonPin, INPUT);
}

void loop() {
  if (!client.connected()) {
    reconnect();
  }

  client.loop();

  // Read yellow button state
  int yellowReading = digitalRead(yellowButtonPin);
  if (yellowReading != lastYellowButtonState && millis() - lastDebounceTime > debounceDelay) {
    lastDebounceTime = millis();
    lastYellowButtonState = yellowReading;
    client.publish("yellow_button_state", String(yellowReading).c_str());
  }

  // Read red button state
  int redReading = digitalRead(redButtonPin);
  if (redReading != lastRedButtonState && millis() - lastDebounceTime > debounceDelay) {
    lastDebounceTime = millis();
    lastRedButtonState = redReading;
    client.publish("red_button_state", String(redReading).c_str());
  }

  // Read blue button state
  int blueReading = digitalRead(blueButtonPin);
  if (blueReading != lastBlueButtonState && millis() - lastDebounceTime > debounceDelay) {
    lastDebounceTime = millis();
    lastBlueButtonState = blueReading;
    client.publish("blue_button_state", String(blueReading).c_str());
  }

  //client.publish("yellow_button_state", String(yellowReading).c_str());
  //client.publish("red_button_state", String(redReading).c_str());
  //client.publish("blue_button_state", String(blueReading).c_str());

  delay(110);
}

void reconnect() {
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    if (client.connect("")) {
      Serial.println("connected");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      delay(5000);
    }
  }
}
