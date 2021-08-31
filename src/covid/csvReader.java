package covid;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class csvReader {
    public static List<Row> readCSV(String fileName){
        // parse csv and return an ArrayList of Rows
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        List<Row> rows = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.UTF_8)){
            br.readLine();
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if(attributes.length != 8){
                    line = br.readLine();
                    continue;
                }
                Date date = format.parse(attributes[3]);
                int cases;
                int deaths;
                int peopleVaccinated;
                try {
                    cases = Integer.parseInt(attributes[4]);
                } catch(NumberFormatException ex) {
                    cases = 0;
                }
                try {
                    deaths = Integer.parseInt(attributes[5]);
                } catch(NumberFormatException ex) {
                    deaths = 0;
                }
                try {
                    peopleVaccinated = Integer.parseInt(attributes[6]);
                } catch(NumberFormatException ex) {
                    peopleVaccinated = 0;
                }

                Row row = new Row(attributes[0],attributes[1],attributes[2],date,cases,deaths,peopleVaccinated);
                rows.add(row);
                line = br.readLine();
            }

        } catch (IOException | ParseException ioe){
            ioe.printStackTrace();
        }
    return rows;
    }

}

