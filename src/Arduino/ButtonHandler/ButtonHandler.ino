#include "Arduino.h"

//1: toggle mode, 2: follow mode
#define LED_MODE   1

const int yellowButtonPin = 1;   // the number of the pushbutton pin for yellow button (terminal right 1)
const int redButtonPin = 5;      // the number of the pushbutton pin for red button (battery port 5 pin 5)
const int blueButtonPin = 8;     // (battery port 2 pin 8)

// Variables will change:
int yellowButtonState;            // the current reading from the yellow button pin
int redButtonState;               // the current reading from the red button pin
int blueButtonState;               // the current reading from the red button pin
int lastYellowButtonState = HIGH; // the previous reading from the yellow button pin
int lastRedButtonState = HIGH;    // the previous reading from the red button pin
int lastBlueButtonState = HIGH;               // the current reading from the red button pin

unsigned long lastDebounceTime = 0;  // the last time the output pin was toggled
unsigned long debounceDelay = 50;    // the debounce time; increase if the output flickers
unsigned long lastLedFadeTime = 0;

void setup() {
  pinMode(yellowButtonPin, INPUT);
  pinMode(redButtonPin, INPUT);
  pinMode(blueButtonPin, INPUT);
}

void loop() {
  // Read yellow button state
  int yellowReading = digitalRead(yellowButtonPin);
  Serial.println(yellowReading);


  // If the yellow button state changed, due to noise or pressing:
  if (yellowReading != lastYellowButtonState) {
    // reset the yellow debounce timer
    lastDebounceTime = millis();
  }

  // Check if debounce delay has passed for yellow button
  if ((millis() - lastDebounceTime) >= debounceDelay) {
    // Update yellow button state if it's still different from the last state
    if (yellowReading != yellowButtonState) {
      yellowButtonState = yellowReading;

    }
  }
  // Update last yellow button state
  lastYellowButtonState = yellowReading;

  // Read red button state
  int redReading = digitalRead(redButtonPin);
  Serial.println(redReading);

  // If the red button state changed, due to noise or pressing:
  if (redReading != lastRedButtonState) {
    // reset the red debounce timer
    lastDebounceTime = millis();
  }

  // Check if debounce delay has passed for red button
  if ((millis() - lastDebounceTime) >= debounceDelay) {
    // Update red button state if it's still different from the last state
    if (redReading != redButtonState) {
      redButtonState = redReading;

    }
  }

  // Update last red button state
  lastRedButtonState = redReading;

  // Read blue button state
  int blueReading = digitalRead(blueButtonPin);
  Serial.println(blueReading);

  // If the vblue button state changed, due to noise or pressing:
  if (blueReading != lastBlueButtonState) {
    // reset the blue debounce timer
    lastDebounceTime = millis();
  }

  // Check if debounce delay has passed for blue button
  if ((millis() - lastDebounceTime) >= debounceDelay) {
    // Update blue button state if it's still different from the last state
    if (blueReading != blueButtonState) {
      blueButtonState = blueReading;

    }
  }

  // Update last blue button state
  lastBlueButtonState = blueReading;
}
