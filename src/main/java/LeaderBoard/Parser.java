package LeaderBoard;

public class Parser {

    public Parser() {}

    public String[] split(String string) {
        String[] strings = string.split("\\r?\\n");

        System.out.println("JBA:" + strings[0]);
        return strings;
    }

    public String[] splitAtComma(String string) {
        String[] strings = string.split(",");

        return strings;
    }

}
