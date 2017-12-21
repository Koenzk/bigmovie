import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public Parser(String pattern, String substitution, String[] header, String IMDBpath, String choice, BufferedReader br){

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(IMDBpath + "CSV/" + choice.toLowerCase() + ".csv")))
        {
            int count = 0; // TODO: Remove count, in de huidige situatie doorloopt hij alleen de eerste 150 regels van het plaintext bestand.

            csvWriter.writeNext(header); // TODO: Nodig?

            while (br.readLine() != null)
            {
                String nextLine = br.readLine();

                Pattern r = Pattern.compile(pattern);

                Matcher matcher = r.matcher(nextLine);

                String result = matcher.replaceAll(substitution);

                String[] line = result.split("-");

                csvWriter.writeNext(line, true);

                count++;

                System.out.println(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}
