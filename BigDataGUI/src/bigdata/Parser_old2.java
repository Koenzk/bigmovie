package bigdata;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser_old2
{
    // Class variables
    private String pattern;
    private String substitution;
    private String[] header;
    private String outpath;
    private BufferedReader br;
    private FileReader fr;
    private String nextLine;
    private String result;
    private Matcher matcher;
    private Pattern r;
    // Constructor
    public Parser_old2(String pattern, String substitution, String[] header, String outpath, BufferedReader br, FileReader fr)
    {
        this.pattern = pattern;
        this. substitution = substitution;
        this.header = header;
        this.outpath = outpath;
        this.br = br;
        this.fr = fr;
    }

    // Method to actually parse the plaintext file into .CSV
    public void parse() throws IOException
    {
        int count = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outpath)))
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
