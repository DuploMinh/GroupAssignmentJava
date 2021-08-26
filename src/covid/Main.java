package covid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws ParseException {
        // write your code here
        List<Row> test = csvReader.readCSV("src/covid/covid-data.csv");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Select the geometric region search type? | 1) By continent | 2) By country | 0 to break");
            int geo = Integer.parseInt(sc.nextLine());
            System.out.println("Select the time range type? | 1) Start and end | 2) Number of days or weeks from a particular date  | 3) Number of days or weeks to a particular date  | 0 to break");
            int timeRange = Integer.parseInt(sc.nextLine());
            System.out.println("Select the date group type? | 1) No grouping | 2) Number of groups | 3) Number of days | 0 to break");
            int group = Integer.parseInt(sc.nextLine());
            System.out.println("Select the metrics? | 1) No grouping | 2) Number of groups | 3) Number of days | 0 to break");
            int metrics = Integer.parseInt(sc.nextLine());
            System.out.println("Select the Result type? | 1) New Total | 2) Up To | 0 to break");
            int resultType = Integer.parseInt(sc.nextLine());
            String continent;
            String country;
            int dateToAdd;
            Date startDate;
            switch (geo) {
                case 0:
                    break;
                case 1:
                    System.out.println("Continent?");
                    continent = sc.nextLine();
                    test = logicHandling.selectByContinent(continent, test);
                case 2:
                    System.out.println("Country?");
                    country = sc.nextLine();
                    test = logicHandling.selectByCountry(country, test);
                default:
                    System.out.println("Invalid argument");
                    continue;
            }
            switch (timeRange) {
                case 0:
                    break;
                case 1:
                    System.out.println("Start date? (dd/MM/yyyy)");
                    startDate = format.parse(sc.nextLine());
                    System.out.println("End date? (dd/MM/yyyy)");
                    Date endDate = format.parse(sc.nextLine());
                    test = logicHandling.selectByDateInc(startDate,endDate,test);
                case 2:
                    System.out.println("Start date? (dd/MM/yyyy)");
                    startDate = format.parse(sc.nextLine());
                    System.out.println("Weeks(1) or days(2)");
                    int addOpt = Integer.parseInt(sc.nextLine());
                    dateToAdd = getDateToAdd(sc, addOpt);
                    test = logicHandling.selectByDateFrom(startDate, dateToAdd, test);
                case 3:
                    System.out.println("Start date? (dd/MM/yyyy)");
                    startDate = format.parse(sc.nextLine());
                    System.out.println("Weeks(1) or days(2)");
                    addOpt = Integer.parseInt(sc.nextLine());
                    dateToAdd = getDateToAdd(sc, addOpt);
                    test = logicHandling.selectByDateTo(startDate, dateToAdd, test);
                default:
                    System.out.println("Invalid argument");
                    continue;
            }
            switch (group){
                case 0:
                case 1:
                case 2:
            }
        } while (true);
    }

    private static int getDateToAdd(Scanner sc, int addOpt) {
        int dateToAdd;
        switch (addOpt) {
            case 1 -> {
                System.out.print("Weeks");
                dateToAdd = Integer.parseInt(sc.nextLine());
                dateToAdd = dateToAdd * 7;
                return dateToAdd;
            }
            case 2 -> {
                System.out.print("Days?");
                dateToAdd = Integer.parseInt(sc.nextLine());
            }
            default -> throw new IllegalStateException("Unexpected value: " + addOpt);
        }
        return dateToAdd;
    }

    static void tabular(Displayable[] display){
        //Tabular display
    }
    static void chart(Displayable[] display){
        //Chart display
    }
    static class Displayable{
        String label;
        String value;

        public Displayable(String label, String value) {
            this.label = label;
            this.value = value;
        }
    }
}
