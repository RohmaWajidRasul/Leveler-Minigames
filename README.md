# Leveler Minigames
_-brought to you by the members of DEV TEAM 14_ 


## _What is Leveler?_

The primary purpose of Leveler Minigames is to provide an engaging entertainment system that also involves thought provoking tasks.

Leveler brings the benefits of exercising critical thinking, adaptation to rapidly changing environments, and time management.    

## _In-depth System Description_

Leveler minigames currently contains three primary minigames: _Bolt_, _Sequence_, and _Color Match_ (the latter of which has two variations, matching by either color or text). This system is composed of two primary surfaces: a graphical user interface and an embedded system controller. The computer surface is responsible for housing the code of the game, the graphical user interface, as well as the power delivery for the controller. The controller will read user input from three categories of Grove sensors that span: three LED buttons, a joystick, and an accelerometer. With a Wio Seeed terminal and battery extension, the sensors will provide various input forms required to play their respective minigame.

## _Getting Started with Leveler_
<details><summary><h3> Installation Prerequisites </h3></summary>

Leveler requires:
- Computer that can run Java JDK 20 or greater (install [Java](https://www.oracle.com/java/technologies/downloads/) from Oracle)
- Maven which can be installed following the instructions [here](https://www.baeldung.com/install-maven-on-windows-linux-mac)  
- Limited edition Leveler custom built controller

</details>

<details><summary><h3> Installation Guide </h3></summary>

Given that the system is having difficulties creating a stable jar file, the following steps work but result in bugs within areas such as the leaderboard and gameover screen. 

**Step 1:** _Obtaining Game Files_
- Go to the [main project page](https://git.chalmers.se/courses/dit113/2024/group-14/minigames)
- Click on the "Code" drop down and either clone the repository (with SSH or HTTPS) or download the source code zip file

**Step 2:** _Building and Compiling the System_ 
- Open your command prompt (or terminal on mac)
- Navigate to the directory (folder) where you cloned/downloaded the game files (don't forget to extract if it's the zip)
- Type the following commands one after the next in the command line:
     - mvn clean
     - mvn install
- These commands will create a folder named "target" among the game files where you can find a jar executable

**Step 3:** _Running the Game_
- Click on the Leveler-1.0-SNAPSHOT-shaded.jar to run the game


**_ALTERNATIVE INSTALLATION AND RUNNING METHOD_**

    ALT Step 1: install an integrated development environment (IDE) of your choice (Intellij Idea, Visual Studio Code, Eclipse)

    ALT Step 2: follow the same steps as the "Obtaining Game Files" for the installation guide.

    ALT Step 3: navigate to the location where the game files are and open them in the IDE


</details>

<details><summary><h3> Controller Details </h3></summary>

Below is a link to an image of the state-of-the-art Leveler controller:

![Leveler_Controller](readmeAssets/LevelerController.jpg)

The following is a link to the layout of the controller sensors and their connections once built into the controller:

![Sensor_Layout](readmeAssets/levelerPortLayout.png)

</details>

## _Hardware and Software Information_

### → The Hardware used for this project include:
- Grove sensors
- WIO SEEED terminal
- Battery extension unit
- Wire connections
- Brick assembly system (lego bricks)

### → The Software used for this project include:
- Java JDK
- JavaFX & Scene Builder
- C++ (for Arduino)
- Maven
- Intellij Idea, VSCode, and Aruino IDEs
- GitLab remote repository system (for git, file organization, and pipeline)
- Git version control system

## _Project Developers and Contributions_

- **Rohma Wajid Rasul**
    > _Contributed by co-developing the Bolt and Color Match minigames, added the arduino cli configuration to the pipeline for the hardware build process, rewrote all the arduino code into a single program split into different files (classes), contributed to the wiki (requirements, issues and the architectural diagram), and code reviews._
- **Zhaoyu Wang (Jerome)**
    > _Contributed by co-developing Color Match minigame, creating game menu, tutorial, code reviews, and co-building the Leveler controller._ 
- **Karl Byland**
    > _Contributed by co-developing Sequence Game minigame, developing the leaderboard, helping with the ci pipeline, bug fixing in the color match mini-game, writing the wiki retrospective, and code reviews._
- **Filipe Rosa**
    > _Contributed by co-developing Bolt game and CI pipeline, co-building Leveler controller and researching arduino, maven, and code reviews._
- **David zum Felde**
    > _Contributed by co-developing Sequence Game minigame, developing game over screen, helping with project wiki, writing project README, co-building Leveler controller, create controller layout diagram, edit demo video, animated Leveler logo, and code reviews._      
## _Product Demo Video_

Feel free to checkout the official demo of the Leveler minigame product, complete with both tiers of the system.  

**→**  [Leveler Product Demo Video](https://youtu.be/CiNcn1dWq7g)