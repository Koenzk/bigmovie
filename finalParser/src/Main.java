import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
    // Main method
    public static void main(String[] args) throws IOException
    {
        // Variables
        String IMDBpath = "/Users/MustiDarsh/Desktop/School/Informatica/My GitHub Repo\'s/bigmovie/Lists/";
        String pattern;
        String substitution;
        String[] header;
        int linesToSkip = 0;
        System.out.println("Which list would you like to export to CSV?");

        // Scanner
        Scanner s = new Scanner(System.in);
        String choice = s.next().toLowerCase();

        //Filereader and bufferedreader
        FileReader fr = new FileReader(IMDBpath + choice.toLowerCase() + ".list");
        BufferedReader br = new BufferedReader(fr);

        // Load correct pattern and substitution based on user input
        switch (choice)
        {
            case "countries":
                pattern = "\"?(.*?)\"?\\s\\((.{4,7}|\\?\\?\\?\\?|\\d{4}\\/.*)\\)\\s*(\\((.*)\\))?\\s*(\\{([^\\{}]*)\\})?\\s(\\{{(SUSPENDED)\\}})?\\s*(.*)";
                substitution = "\"$1\",$2\",\"$4\",\"$6\",\"$8\",\"$9\"";
                header = new String[]{};
                linesToSkip = 14;
                break;
            case "movies":
                pattern = "\\s([^\"].*[^\"])\\s\\((.{4})\\)\\s(\\((.{1,2})\\))?\\s*(\\{{(.*?)\\}})?\\s*(\\d{4}|\\?{4})";
                substitution = "\\1,\\2,\\4,\\6,\\7";
                header = new String[]{};
                linesToSkip = 15;
                break;
            case "series":
                pattern = "\\\"(.*?)\\\"\\s\\((.*?)\\)\\s(\\{([^\\{].*?[^\\}])\\})?\\s*(\\{{(.*?)\\}})?\\s*(.{4,9})";
                substitution = "\\1 \\2 \\4 \\6 \\7";
                header = new String[]{};
                linesToSkip = 15;
                break;
            case "actors":
                pattern = "(.*?)(\\t{1,3})(.+?(?=\\())(\\s+)?(\\((.+?(?=\\)))\\))(\\s)(\\{(.+)\\})?( +)?(\\((\\w{1})\\))?( +)?(\\((.*)\\))?( +)?(\\[(.+)\\])?( +)?(\\<(.*)\\>)?";
                substitution = "\\1 \\3 \\6 \\9 \\12 \\15 \\18";
                header = new String[]{};
                linesToSkip = 239;
                break;
            case "actresses":
                pattern = "(.*?)(\\t{1,3})(.+?(?=\\())(\\s+)?(\\((.+?(?=\\)))\\))(\\s)(\\{(.+)\\})?( +)?(\\((\\w{1})\\))?( +)?(\\((.*)\\))?( +)?(\\[(.+)\\])?( +)?(\\<(.*)\\>)?";
                substitution = "\\1 \\3 \\6 \\9 \\12 \\15 \\18";
                header = new String[]{};
                linesToSkip = 241;
                break;
            case "directors":
                pattern = "(.*?)(\\t{1,3})(.+?(?=\\())(\\s+)?(\\((.+?(?=\\)))\\))(\\s)(\\{(.+)\\})?( +)?(\\((\\w{1})\\))?( +)?(\\((.*)\\))?( +)?(\\[(.+)\\])?( +)?(\\<(.*)\\>)?";
                substitution = "\\1 \\3 \\6 \\9 \\12 \\15 \\18";
                header = new String[]{};
                linesToSkip = 235;
                break;
            case "producers":
                pattern = "(.*?)(\\t{1,3})(.+?(?=\\())(\\s+)?(\\((.+?(?=\\)))\\))(\\s)(\\{(.+)\\})?( +)?(\\((\\w{1})\\))?( +)?(\\((.*)\\))?( +)?(\\[(.+)\\])?( +)?(\\<(.*)\\>)?";
                substitution = "\\1 \\3 \\6 \\9 \\12 \\15 \\18";
                header = new String[]{};
                linesToSkip = 219;
                break;
            case "ratings":
                pattern = "(.{20}) ([0-9]\\.[0-9])  (.+) (?:\\((\\d{4}|\\?{4})(?:\\/([IVXCM]+))?\\)) ?(\\{(.+)\\}?)?";
                substitution = "$2; $3; $4; $7";
                header = new String[]{};
                linesToSkip = 28;
                break;
            case "running-times":
                pattern = "(?:\"|)([^\"\\n]*)\"? \\((\\d{4}|[?]{4})\\W(?:.*\\{|.*\\))?(.*\\))?(?:.*\\t|.*:)((\\d)?(\\d)?(\\d)?(\\d))(?:.*)";
                substitution = "\\1, \\2, \\3, \\4";
                header = new String[]{};
                linesToSkip = 14;
                break;
            default:
                pattern = "";
                substitution = "";
                header = new String[]{};
                System.out.println("List not found!");
                System.exit(0);
                break;
        }

        Parser parser = new Parser(pattern, substitution, header, IMDBpath, choice, br, fr, linesToSkip);
        parser.parse();
    }

}