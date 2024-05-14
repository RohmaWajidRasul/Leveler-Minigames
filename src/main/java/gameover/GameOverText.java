package gameover;

public class GameOverText {
    
    private String specificText;

    public String setGameOverTextNewbie(int randomNewbieElement) {

        String[] gameOverNewbieTxtArr = { //newbie string scenarios levels 2-15
            "LEVELER MET THE INEVITABLE ON LEVEL ",
            "UNREMARKABLY, LEVELER DIED ON LEVEL ",
            "LEVELER GOT COOKED ON LEVEL ",
            "LEVELER ALMOST PROVED A POINT ON LEVEL " ,
            "LEVELER BARELY TRIED TO SURVIVE LEVEL ",
            "LEVELER CHOSE ALT-F4 ON LEVEL ",
            "LEVELER LEFT THE GROUP CHAT ON LEVEL ",
            "LEVELER HAS SPIRIT, BUT GAVE IT UP ON LEVEL ",
            "LEVELER FELT 5 STAGES OF GRIEF ON LEVEL ",
            "LEVLER WAS PUT TO REST ON LEVEL ",
            "LEVELER, STRIVE FOR BETTER. IT'S LEVEL " ,
            "LEVELER WAS 'SOLO-LEVELING' ON LEVEL ",
            "YOU FELT SKILL ISN'T YOUR THING ON LEVEL ",
            "WOW, LEVELER GOT PATRONIZED ON LEVEL ",
            "LEVELER, GAMERAGE IS WHAT YOU FEEL ON LEVEL "
        };

        specificText = gameOverNewbieTxtArr[randomNewbieElement];
        return specificText;
    }

    public String setGameOverTextPro(int randomProElement) {

        String[] gameOverProTxtArr = { //pro string scenarios levels 16-25
            "LEVELER LEFT FOR KEBAB PIZZA ON LEVEL ",
            "LEVELER HELD THE LINE UP UNTIL LEVEL ",
            "LEVELER BUMPED INTO AUTOMATONS ON LEVEL ",
            "LEVELER WENT MAINCHARACTER ON LEVEL ",
            "LEVELER BECAME A LEGEND ON LEVEL ",
            "LEVELER WAS 'PRO-GAMING' UNTIL ",
            "LEVELER EMBRACED DESERVED REST ON LEVEL ",
            "LEVELER LEFT FOR SWEDISH FIKA ON LEVEL ",
            "LEVELER USED ULTRA INSTICT UNTIL LEVEL ",
            "LEVELER LEFT THE MATRIX ON LEVEL "
        };

        specificText = gameOverProTxtArr[randomProElement];
        return specificText;
    }
}

