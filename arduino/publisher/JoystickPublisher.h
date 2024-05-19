#ifndef JOYSTICKPUBLISHER_H
#define JOYSTICKPUBLISHER_H

#include "Arduino.h"
#include "PubSubClient.h"

class JoystickPublisher {
public:
    JoystickPublisher();
    void setup(PubSubClient &mqttClient, const char* topic);
    void publishData();
private:
    const char* TOPIC;
    PubSubClient* mqttClientPtr;
};

#endif
