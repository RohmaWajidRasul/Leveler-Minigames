#include "rpcWiFi.h"
#include "PubSubClient.h"

WiFiClient wifiClient;
PubSubClient client(wifiClient);

const char* ssid = "Inky pinkie ponkie";
const char* password =  "gggggggg";
const char* TOPIC = "SequenceMini-game/Joystick";

void setup() {

    Serial.begin(115200);
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

void loop() { //https://wiki.seeedstudio.com/Grove-Thumb_Joystick/

  if (!client.connected()) {
    reconnect();
  }
  client.loop();
    String direction = "";


    int sensorValue1 = analogRead(A0);
    int sensorValue2 = analogRead(A1);

    Serial.print("The X and Y coordinate is:");
    Serial.print(sensorValue1, DEC);
    Serial.print(",");
    Serial.println(sensorValue2, DEC);
    Serial.println(" ");

    if (sensorValue1 > 600) {
      Serial.print("DOWN");
      direction = "DOWN";
      //client.publish(TOPIC,"DOWN");


    } else if (sensorValue1 < 400) {
      Serial.print("Up");
      direction = "UP";
      //client.publish(TOPIC,"UP");

    }

    if (sensorValue2 > 600) {
      Serial.print("Right");
      direction = "RIGHT";
      //client.publish(TOPIC,"RIGHT");


    } else if (sensorValue2 < 400) {
      Serial.print("Left");
      direction = "LEFT";
      //client.publish(TOPIC,"LEFT");
    }
    Serial.println("");
    if (direction != "") {
    char* cstr = new char [direction.length() + 1];
    client.publish(TOPIC,direction.c_str());
    }
    delay(300);


}

void reconnect() { //https://github.com/knolleary/pubsubclient/blob/master/examples/mqtt_basic/mqtt_basic.ino
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("PhantomOfTheOpera")) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish(TOPIC,"inky pinkie ponkie");
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