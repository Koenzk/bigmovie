package com.Parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    // Constructor
    public Parser(String pattern, String substitution, String[] header, String IMDBpath, String choice, BufferedReader br)
    {
        this.pattern = pattern;
        this. substitution = substitution;
        this.header = header;
        this.IMDBpath = IMDBpath;
        this.choice = choice;
        this.br = br;
    }

    // Method to actually parse the plaintext file into .CSV
    public void parse() throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(IMDBpath + "CSV/" + choice.toLowerCase() + ".csv")))
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
