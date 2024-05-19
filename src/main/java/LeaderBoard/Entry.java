package LeaderBoard;

public class Entry {

    private final String name;
    private final int score;

    public Entry(String name,int score) {

        this.score = score;
        this.name = name;
    }
    public String toString() {
        return this.name + " = " + this.score;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

}
