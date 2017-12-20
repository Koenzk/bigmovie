import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        String choice;
        System.out.println("Wich list you want to export to csv?");
        System.out.println("Countries?");

        //Scanner
        Scanner s = new Scanner(System.in);
        choice = s.next();

        List<String[]> entries = new ArrayList<>();

        //Filereader and bufferedreader
        FileReader fr = new FileReader("D:\\listfiles\\" + choice.toLowerCase() + ".list");
        BufferedReader br = new BufferedReader(fr);

        while (br.readLine() != null) {
            String nextLine = br.readLine();
            String countriesPattern = "\"?(.*?)\"?\\s\\((\\d{4}|\\?\\?\\?\\?|\\d{4}\\/.*)\\)\\s*(\\((.*)\\))?\\s*(\\{([^\\{}]*)\\})?\\s(\\{(SUSPENDED)\\}})?\\s*(.*)";
            Pattern r = Pattern.compile(countriesPattern);
            String subst = "$1, $2, $4, $6, $8, $9";
            Matcher matcher = r.matcher(nextLine);
            String result = matcher.replaceAll(subst);
            String [] line = {result};
            entries.add(line);
            if(entries.size() == 100){
                try (CSVWriter writer = new CSVWriter(new FileWriter("D:\\listfiles\\" + choice.toLowerCase() + ".csv"))) {
                    writer.writeAll(entries);
                }
            }
        }



        /*switch (choice) {

            case "Countries":

                break;

            case "Movies":

                break;

            case "Series":

                break;

            case "Actors":

                break;

            case "Actresses":

                break;

            case "Directors":

                break;

            case "Producers":

                break;

            case "Ratings":

                break;

            case "Running times":

                break;

        }*/
    }
}
