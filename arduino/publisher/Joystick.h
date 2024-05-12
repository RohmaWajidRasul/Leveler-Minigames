#ifndef JOYSTICK_H
#define JOYSTICK_H

#include "Arduino.h"
#include "PubSubClient.h"

class Joystick {
public:
    Joystick();
    void setup(PubSubClient &mqttClient, const char* topic);
    void publishData();
private:
    const char* TOPIC;
    PubSubClient* mqttClientPtr;
};

#endif
