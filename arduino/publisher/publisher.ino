#include "rpcWiFi.h"
#include "PubSubClient.h"
#include <Wire.h>
#include "AccelerometerPublisher.h"
#include "JoystickPublisher.h"
#include "ButtonPublisher.h"
#include "WifiInfo.h"



AccelerometerPublisher accelerometerPublisher;
JoystickPublisher joystickPublisher;
ButtonPublisher buttonPublisher(1, 5, 8); 

WiFiClient wifiClient;
PubSubClient client(wifiClient);

const char* ssid = WIFI_SSID;
const char* password =  WIFI_PASSWORD;

unsigned long lastAccelTime = 0;
unsigned long lastButtonsTime = 0;

//https://github.com/knolleary/pubsubclient/blob/master/examples/mqtt_esp8266/mqtt_esp8266.ino
//Part of method taken from this link
void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("")) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish("Topic 1","It works");
      // ... and resubscribe
      client.subscribe("inTopic");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

//https://github.com/knolleary/pubsubclient/blob/master/examples/mqtt_esp8266/mqtt_esp8266.ino
//Part of method taken from this link
void setup() {

    accelerometerPublisher.init(client, "sensor/vibration");
    Serial.begin(9600);
    //Serial.begin(115200);
    while(!Serial); // Wait for Serial to be ready

    // Set WiFi to station mode and disconnect from an AP if it was previously connected
    WiFi.mode(WIFI_STA);
    WiFi.disconnect();

    Serial.println("Connecting to WiFi..");
    WiFi.begin(ssid, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.println("Connecting to WiFi..");
        WiFi.begin(ssid, password);
    }
    Serial.println("Connected to the WiFi network");
    Serial.print("IP Address: ");
    Serial.println (WiFi.localIP()); // prints out the device's IP address

    client.setServer("test.mosquitto.org", 1883);
    joystickPublisher.setup(client, "sensor/direction");
    buttonPublisher.setup(client, "sensor/yellow_button_state", "sensor/red_button_state", "sensor/blue_button_state");
    

}
void loop() {
  //Serial.print("STARTED");

   if (!client.connected()) {
    reconnect();
  } else {
    client.loop();

    unsigned long currentTime = millis();

    if (currentTime - lastAccelTime >= 300) {
      accelerometerPublisher.publishData();
      joystickPublisher.publishData();
      lastAccelTime = currentTime;
    }

    if (currentTime - lastButtonsTime >= 110) {
      buttonPublisher.publishData();
      lastButtonsTime = currentTime;

    }


  }

}
