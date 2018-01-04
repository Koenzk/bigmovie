import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{
    // Class variables
    private String pattern;
    private String substitution;
    private String[] header;
    private String IMDBpath;
    private String choice;
    private BufferedReader br;
    private FileReader fr;
    private String nextLine;
    private String result;
    private Matcher matcher;
    private Pattern r;
    private int linesToSkip;
    // Constructor
    public Parser(String pattern, String substitution, String[] header, String IMDBpath, String choice, BufferedReader br, FileReader fr, int linesToSkip)
    {
        this.pattern = pattern;
        this.substitution = substitution;
        this.header = header;
        this.IMDBpath = IMDBpath;
        this.choice = choice;
        this.br = br;
        this.fr = fr;
        this.linesToSkip = linesToSkip;
    }

    // Method to actually parse the plaintext file into .CSV
    public void parse() throws IOException
    {
        int lineNumber = 0;
        int lts = this.linesToSkip;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(IMDBpath + "CSV/" + choice.toLowerCase() + ".csv")))
        {
            r = Pattern.compile(pattern);
            while ((nextLine = br.readLine()) != null) {
                if (++lineNumber <= lts) continue;

                //if (!nextLine.startsWith("\"")) {
//                    System.out.println(nextLine);
                    matcher = r.matcher(nextLine);

                    result = matcher.replaceAll(substitution);
                    System.out.println(result);
                    if (result.length() != 0) {
                        writer.write(result);
                        writer.newLine();

                    }
                //} else continue;
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
