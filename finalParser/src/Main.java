import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        // Constants
        String IMDBpath = "D:\\listfiles\\"; // TODO: !!

        // Variables
        String pattern;
        String substitution;
        String[] header;

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
                pattern = "\"?(.*?)\"?\\s\\((.{4,7}|\\?\\?\\?\\?|\\d{4}\\/.*)\\)\\s*(\\((.*)\\))?\\s*(\\{([^\\{}]*)\\})?\\s(\\{\\{(SUSPENDED)\\}\\})?\\s*(.*)";
                substitution = "\"$1\",\"$2\",\"$4\",\"$6\",\"$8\",\"$9\"";
                header = new String[]{};
                Parser parserCountries = new Parser(pattern, substitution, header, IMDBpath, choice, br);
                break;
            case "movies":
                pattern = "";
                substitution = "";
                header = new String[]{};
                Parser parserMovies = new Parser(pattern, substitution, header, IMDBpath, choice, br);
                break;
            case "series":
                pattern = "\"(.*?)\\\"\\s\\((.*?)\\)\\s(\\{([^\\{].*[^\\}])\\})?(\\{(.*?)\\}})?\\s*(.*)";
                substitution = "$1-$2-$4-$6-$7";
                header = new String[]{};
                Parser parserSeries = new Parser(pattern, substitution, header, IMDBpath, choice, br);
                break;
            case "actors":
                pattern = "";
                substitution = "";
                header = new String[]{};
                break;
            case "actresses":
                pattern = "";
                substitution = "";
                header = new String[]{};
                break;
            case "directors":
                pattern = "";
                substitution = "";
                header = new String[]{};
                break;
            case "producers":
                pattern = "";
                substitution = "";
                header = new String[]{};
                break;
            case "ratings":
                pattern = "";
                substitution = "";
                header = new String[]{};
                break;
            case "running-times":
                pattern = "(?:\")(.*)(?:\") \\((\\d{4}|[?]{4})\\W(?:.*\\{|.*\\))?(.*\\))?(?:.*\\t|.*:)((\\d)?(\\d))(?:.*)";
                substitution = "\\1, \\2, \\3, \\4";
                header = new String[]{};
                break;
            default:
                pattern = "";
                substitution = "";
                header = new String[]{};
                System.out.println("List not found!");
                System.exit(0);
                break;
        }
    }

}
