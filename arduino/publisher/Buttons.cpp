#include "Buttons.h"

// Variables will change:
int yellowButtonState;            // Yellow button state
int redButtonState;               // Red button state
int blueButtonState;              // Blue button state
int lastYellowButtonState = HIGH; // Previous yellow button state
int lastRedButtonState = HIGH;    // Previous red button state
int lastBlueButtonState = HIGH;   // Previous blue button state

unsigned long lastDebounceTime = 0;  // Last time the output pin was toggled
unsigned long debounceDelay = 50;    // Debounce time; increase if output flickers

Buttons::Buttons(int yellowPin, int redPin, int bluePin) : yellowPin(yellowPin), redPin(redPin), bluePin(bluePin) {}

void Buttons::setup(PubSubClient &mqttClient,  const char* yellowTopic, const char* redTopic, const char* blueTopic) {
  pinMode(yellowPin, INPUT);
  pinMode(redPin, INPUT);
  pinMode(bluePin, INPUT);

  mqttClientPtr = &mqttClient;
  YELLOW_TOPIC = yellowTopic;
  RED_TOPIC = redTopic;;
  BLUE_TOPIC = blueTopic;;

}

void Buttons::publishData() {
  // Read yellow button state
  int yellowReading = digitalRead(yellowPin);
  if (yellowReading != lastYellowButtonState && millis() - lastDebounceTime > debounceDelay) {
    lastDebounceTime = millis();
    lastYellowButtonState = yellowReading;
    mqttClientPtr->publish(YELLOW_TOPIC, String(yellowReading).c_str());
  }

  // Read red button state
  int redReading = digitalRead(redPin);
  if (redReading != lastRedButtonState && millis() - lastDebounceTime > debounceDelay) {
    lastDebounceTime = millis();
    lastRedButtonState = redReading;
    mqttClientPtr->publish(RED_TOPIC, String(redReading).c_str());
  }

  // Read blue button state
  int blueReading = digitalRead(bluePin);
  if (blueReading != lastBlueButtonState && millis() - lastDebounceTime > debounceDelay) {
    lastDebounceTime = millis();
    lastBlueButtonState = blueReading;
    mqttClientPtr->publish(BLUE_TOPIC, String(blueReading).c_str());
  }
}
