package covid;

import java.util.Calendar;
import java.util.Date;

public class Row {
    // This class represents each row of the CSV file
    private String iso;
    String continent;
    private String location;
    private Date date;
    private int cases;
    private int deaths;
    private int peopleVaccinated;

    public Row(String iso, String continent, String location, Date date, int cases, int deaths, int peopleVaccinated) {
        this.iso = iso;
        this.continent = continent.replaceAll("\\s+","");
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

    public boolean isInDateRange(Date startDate, Date endDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c1.add(Calendar.DAY_OF_MONTH, -1);
        c2.setTime(endDate);
        c2.add(Calendar.DAY_OF_MONTH, 1);
        return this.date.before(c2.getTime()) && this.date.after(c1.getTime());
    }
}
