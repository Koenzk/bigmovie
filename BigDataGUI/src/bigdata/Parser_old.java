package bigdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser_old
{
    // Class variables
    private String pattern;
    private String substitution;
    private String[] header;
    private String outpath;
    private BufferedReader br;

    // Constructor
    public Parser_old(String pattern, String substitution, String[] header, BufferedReader br, String outpath)
    {
        this.pattern = pattern;
        this.substitution = substitution;
        this.header = header;
        this.br = br;
        this.outpath = outpath;
    }

    // Method to actually parse the plaintext file into .CSV
    public void parse() throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outpath)))
        {
            while (br.readLine() != null)
            {
                String nextLine = br.readLine();

                Pattern r = Pattern.compile(pattern);

                Matcher matcher = r.matcher(nextLine);

                String result = matcher.replaceAll(substitution);

                writer.write(result);
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
