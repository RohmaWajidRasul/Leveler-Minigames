#include "rpcWiFi.h"
#include "PubSubClient.h"
#include "LIS3DHTR.h"
#include <Wire.h>
#include "MMA7660.h"

MMA7660 accelemeter;

WiFiClient wifiClient;
PubSubClient client(wifiClient);

const char* ssid = "Rohma's phone";
const char* password =  "12345678";
void setup() {

    accelemeter.init();
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

}

void loop() {

  if (!client.connected()) {
    reconnect();
  } else {
    client.loop();

    int8_t x;
    int8_t y;
    int8_t z;
    float ax, ay, az;
    accelemeter.getXYZ(&x, &y, &z);

    Serial.print("x = ");
    Serial.println(x);
    Serial.print("y = ");
    Serial.println(y);
    Serial.print("z = ");
    Serial.println(z);

    accelemeter.getAcceleration(&ax, &ay, &az);
    Serial.println("accleration of X/Y/Z: ");
    Serial.print(ax);
    Serial.println(" g");
    Serial.print(ay);
    Serial.println(" g");
    Serial.print(az);
    Serial.println(" g");
    Serial.println("*************");
    Serial.print(az - 0.95);
    Serial.println("g");
    Serial.println("*************");

    char charArray[10];
    sprintf(charArray, "%f", az);

  
    client.publish("Topic 1", charArray);

    delay(500);

  }
}

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