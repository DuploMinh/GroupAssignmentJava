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
import java.util.Calendar;

public class csvReader {
    public static List<Row> readCSV(String fileName){
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

class Row{
    private String iso;
    private String continent;
    private String location;
    private Date date;
    private int cases;
    private int deaths;
    private int peopleVaccinated;

    public Row(String iso, String continent, String location, Date date, int cases, int deaths, int peopleVaccinated) {
        this.iso = iso;
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.cases = cases;
        this.deaths = deaths;
        this.peopleVaccinated = peopleVaccinated;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getPeopleVaccinated() {
        return peopleVaccinated;
    }

    public void setPeopleVaccinated(int peopleVaccinated) {
        this.peopleVaccinated = peopleVaccinated;
    }

    public boolean isInDateRange(Date startDate, Date endDate){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c1.add(Calendar.DAY_OF_MONTH, -1);
        c2.setTime(endDate);
        c2.add(Calendar.DAY_OF_MONTH, 1);
        return this.date.before(c2.getTime())&&this.date.after(c1.getTime());
    }
}