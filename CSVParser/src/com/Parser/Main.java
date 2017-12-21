package com.Parser;

import com.opencsv.CSVWriter;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        // Constants
        String IMDBpath = "/home/koenzk/Downloads/IMDB/"; // TODO: !!

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
                pattern = "\"?(.*?)\"?\\s\\((\\d{4}|\\?\\?\\?\\?|\\d{4}\\/.*)\\)\\s*(\\((.*)\\))?\\s*(\\{([^\\{}]*)\\})?\\s(\\{\\{(SUSPENDED)\\}\\})?\\s*(.*)";
                substitution = "$1-$2-$4-$6-$8-$9";
                header = new String[]{};
                break;
            case "movies":
                pattern = "\\s([^\"].*[^\"])\\s\\((.{4})\\)\\s(\\((.{1,2})\\))?\\s*(\\{\\{(.*?)\\}})?\\s*(\\d{4}|\\?{4})";
                substitution = "$1-$2-$4-$6-$7";
                header = new String[]{};
                break;
            case "series":
                pattern = "\\\"(.*?)\\\"\\s\\((.*?)\\)\\s(\\{([^\\{].*[^\\}])\\})?(\\{\\{(.*?)\\}})?\\s*(.*)";
                substitution = "$1-$2-$4-$6-$7";
                header = new String[]{};
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
                pattern = "";
                substitution = "";
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

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(IMDBpath + "CSV/" + choice.toLowerCase() + ".csv")))
        {
            int count = 0; // TODO: Remove count, in de huidige situatie doorloopt hij alleen de eerste 150 regels van het plaintext bestand.

            //csvWriter.writeNext(header); // TODO: Nodig?

            while (br.readLine() != null && count < 150)
            {
                String nextLine = br.readLine();

                Pattern r = Pattern.compile(pattern);

                Matcher matcher = r.matcher(nextLine);

                String result = matcher.replaceAll(substitution);

                String[] line = result.split("-");

                csvWriter.writeNext(line, true);

                count++;
            }
        }
    }
}
