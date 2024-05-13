#ifndef BUTTONS_H
#define BUTTONS_H

#include "Arduino.h"
#include "PubSubClient.h"

class Buttons {
  public:
    Buttons(int yellowPin, int redPin, int bluePin);
    void setup(PubSubClient &mqttClient, const char* yellowTopic, const char* redTopic, const char* blueTopic);
    void publishData();

  private:
    int yellowPin;
    int redPin;
    int bluePin;

    const char* YELLOW_TOPIC;
    const char* RED_TOPIC;
    const char* BLUE_TOPIC;
    PubSubClient* mqttClientPtr;
};

#endif
