# Leveler Minigames
_-brought to you by the members of DEV TEAM 14_ 



## _Leveler System Description_

Leveler minigames currently contains three primary minigames: _Bolt_, _Sequence_, and _Color Match_ (the latter of which has two variations, matching by either color or text). This system is composed of two primary surfaces: a graphical user interface and an embedded system controller. The computer surface is responsible for housing the code of the game, the graphical user interface, as well as the power delivery for the controller. The controller will read user input from three categories of Grove sensors that span: three LED buttons, a joystick, and an accelerometer. With a Wio Seeed terminal and battery extension, the sensors will provide various input forms required to play their respective minigame.

## _Getting Started with Leveler_

### → Prerequisites Before Installation

Leveler requires:
- Computer that can run Java JDK 20 or greater (install [Java](https://www.oracle.com/java/technologies/downloads/) from Oracle)
- Maven which can be installed following the instructions [here](https://www.baeldung.com/install-maven-on-windows-linux-mac)  
- Limited edition Leveler custom built controller

### → Installation Guide

Step 1: Obtaining game repository files
- Go to the [main project page](https://git.chalmers.se/courses/dit113/2024/group-14/minigames)
- Click on the "Code" drop down and either clone the repository (with SSH or HTTPS) or download the source code zip file

Step 2: Running the system 
- Open your command prompt (or terminal on mac)
- Navigate to the directory (folder) where you cloned/downloaded the game files (don't forget to extract if it's the zip)
- Type the following commands one after the next in the command line:
- mvn clean
- mvn install
- These commands will create a folder named "target" among the game files where you can find a jar executable

Step 3: 
- Click on the Leveler-1.0-SNAPSHOT-shaded.jar to run the game
- Enjoy Leveler!

### → Controller Details

Below is a link to an image of the state-of-the-art Leveler controller:

![Leveler_Controller](https://git.chalmers.se/courses/dit113/2024/group-14/minigames/-/blob/18-readme-revision/src/main/resources/readmeAssets/LevelerController.jpg?ref_type=heads)

The following is a link to the layout of the controller sensors and their connections once built into the controller:

![Sensor_Layout](https://git.chalmers.se/courses/dit113/2024/group-14/minigames/-/blob/18-readme-revision/src/main/resources/readmeAssets/LEVELER%20PORT%20LAYOUT.png?ref_type=heads)

## _Hardware and Software Information_

### → The Hardware used for this project include:
- Grove sensors
- WIO SEEED terminal
- Battery extension unit
- Wire connections
- Brick assembly system (lego bricks)

### → The Software used for this project include:
- Java JDK
- JavaFX
- C++ (for Arduino)
- Maven
- Intellij Idea, VSCode, and Aruino IDEs
- GitLab remote repository system (for git, file organization, and pipeline)
- Git version control system

## _Project Developers and Contributions_

- **Rohma Wajid Rasul**
    > (INSERT CONTRIBUTION)
- **Zhaoyu Wang (Jerome)**
    > (INSERT CONTRIBUTION)
- **Karl Byland**
    > (INSERT CONTRIBUTION)
- **Filipe Rosa**
    > Contributed by co-developing Bolt game and CI pipeline, co-building Leveler controller and researching arduino and maven.
- **David zum Felde**
    > _Contributed by co-developing Sequence Game minigame, developing game over screen, helping with project wiki, writing project README, and co-building Leveler_ controller.      
## _Product Video Demo_


The Leveler Minigames product [video demo](https://youtu.be/CiNcn1dWq7g) can be found on YouTube.