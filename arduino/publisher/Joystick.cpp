#include "Joystick.h"

// Define constants for joystick thresholds and debounce time
const int JOYSTICK_THRESHOLD = 50; // Threshold for joystick movement detection
const unsigned long DEBOUNCE_DELAY = 50; // Debounce time in milliseconds

Joystick::Joystick() {}

void Joystick::setup(PubSubClient &mqttClient, const char* topic) {
    mqttClientPtr = &mqttClient;
    TOPIC = topic;

}

void Joystick::publishData() {
    String direction = "";

    int sensorValue1 = analogRead(A2);
    int sensorValue2 = analogRead(A3);

    static unsigned long lastDebounceTime1 = 0;
    static unsigned long lastDebounceTime2 = 0;
    unsigned long currentMillis = millis();

    // Debounce for sensorValue1
    if (currentMillis - lastDebounceTime1 >= DEBOUNCE_DELAY) {
        lastDebounceTime1 = currentMillis;
        if (sensorValue1 > 600) {
            Serial.print("DOWN");
            direction = "DOWN";
        } else if (sensorValue1 < 400) {
            Serial.print("UP");
            direction = "UP";
        }
    }

    // Debounce for sensorValue2
    if (currentMillis - lastDebounceTime2 >= DEBOUNCE_DELAY) {
        lastDebounceTime2 = currentMillis;
        if (sensorValue2 > 600) {
            Serial.print("RIGHT");
            direction = "RIGHT";
        } else if (sensorValue2 < 400) {
            Serial.print("LEFT");
            direction = "LEFT";
        }
    }

    Serial.println("");
    
    // Publish direction
    if (direction != "") {
    char* cstr = new char [direction.length() + 1];
    mqttClientPtr->publish(TOPIC, direction.c_str());
    }
    
}
