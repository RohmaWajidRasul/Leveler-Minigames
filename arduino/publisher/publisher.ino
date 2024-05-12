#include "rpcWiFi.h"
#include "PubSubClient.h"
#include <Wire.h>
#include "Accelerometer.h"
#include "Joystick.h"
#include "Buttons.h"


Accelerometer accelerometer;
Joystick joystick;
Buttons buttons(1, 5, 8);   // Yellow button pin (terminal right)

WiFiClient wifiClient;
PubSubClient client(wifiClient);

const char* ssid = "R15-C712";
const char* password =  "kmnmq78963";

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

void setup() {

    accelerometer.init(client, "sensor/vibration");
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
    joystick.setup(client, "sensor/direction");
    buttons.setup(client, "sensor/yellow_button_state", "sensor/red_button_state", "sensor/blue_button_state");
    

}
void loop() {
 Serial.print("STARTED");

   if (!client.connected()) {
    reconnect();
  } else {
    client.loop();

    accelerometer.publishData();
    joystick.publishData();
    buttons.publishData();
          
    delay(300);

  }

}
