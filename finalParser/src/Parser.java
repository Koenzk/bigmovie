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
    // Constructor
    public Parser(String pattern, String substitution, String[] header, String IMDBpath, String choice, BufferedReader br, FileReader fr)
    {
        this.pattern = pattern;
        this. substitution = substitution;
        this.header = header;
        this.IMDBpath = IMDBpath;
        this.choice = choice;
        this.br = br;
        this.fr = fr;
    }

    // Method to actually parse the plaintext file into .CSV
    public void parse() throws IOException
    {
        int count = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(IMDBpath + "CSV/" + choice.toLowerCase() + ".csv")))
        {
            r = Pattern.compile(pattern);
            while ((nextLine = br.readLine()) != null)
            {
                matcher = r.matcher(nextLine);

                result = matcher.replaceAll(substitution);

                if(result.length() != 0){
                    count++;
                    writer.write(result);
                    writer.newLine();
                }
            }
            writer.close();
            System.out.println(count);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
