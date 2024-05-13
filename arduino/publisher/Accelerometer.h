#ifndef ACCELEROMETER_H
#define ACCELEROMETER_H

#include "Arduino.h"
#include "LIS3DHTR.h"
#include "MMA7660.h" // Include the header file for MMA7660
#include "PubSubClient.h"


class Accelerometer {
public:
    Accelerometer();
    void init(PubSubClient &mqttClient, const char* topic);
    void publishData();
    void getAcceleration(float* ax, float* ay, float* az);
    void getXYZ(int8_t* x, int8_t* y, int8_t* z);
private:
    MMA7660 accelerationSensor; // Declare MMA7660 object as a member variable
    PubSubClient* mqttClientPtr;
    const char* TOPIC;

};

#endif
