package LeaderBoard;

public class Entry {

    String name;
    int score;

    public Entry(String name,int score) {

        this.score = score;
        this.name = name;

    }

    public String toString() {
        return this.name + " = " + this.score;
    }

}
