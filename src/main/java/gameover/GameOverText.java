package gameover;

public class GameOverText {
    
    public String setGameOverTextNewbie(int randomNewbieElement) {

        String[] gameOverNewbieTxtArr = {

            "LEVELER MET THE INEVITABLE ON LEVEL ", //(GOS) GAME OVER SCREEN NEWBIE SPLASHES 1-15
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

        String specificText = gameOverNewbieTxtArr[randomNewbieElement];
        return specificText;
    }

    public String setGameOverTextPro(int randomProElement) {

        String[] gameOverProTxtArr = {

            "LEVELER LEFT FOR KEBAB PIZZA ON LEVEL ", //(GOS) GAME OVER SCREEN PRO SPLASHES 16-25
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

        String specificText = gameOverProTxtArr[randomProElement];
        return specificText;
    }
}

