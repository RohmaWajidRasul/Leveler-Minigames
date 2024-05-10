#include "Accelerometer.h"

Accelerometer::Accelerometer() {}

void Accelerometer::init(PubSubClient &mqttClient, const char* topic) {
    mqttClientPtr = &mqttClient;
    TOPIC = topic;
    accelerationSensor.init(); // Initialize the MMA7660 object
}

void Accelerometer::publishData() {
    int8_t x;
    int8_t y;
    int8_t z;
    float ax, ay, az;
    accelerationSensor.getXYZ(&x, &y, &z);

    Serial.print("x = ");
    Serial.println(x);
    Serial.print("y = ");
    Serial.println(y);
    Serial.print("z = ");
    Serial.println(z);

    accelerationSensor.getAcceleration(&ax, &ay, &az);
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

    // Publishing sensor data
    char charArray[10];
    sprintf(charArray, "%f", az);
    mqttClientPtr->publish(TOPIC, charArray);
}


