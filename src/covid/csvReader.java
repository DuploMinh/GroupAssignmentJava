package covid;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                Date date = format.parse(attributes[3]);
                Row row = new Row(attributes[0],attributes[1],attributes[2],date,Integer.parseInt(attributes[4]),Integer.parseInt(attributes[5]),Integer.parseInt(attributes[6]));
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
}