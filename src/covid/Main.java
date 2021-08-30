package covid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws ParseException {
        List<Row> data = csvReader.readCSV("src/covid/covid-data.csv");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Select the geographic region search type? | 1) By continent | 2) By country | 0 to break");
            int geo = Integer.parseInt(sc.nextLine());
            if (geo==0){break;}
            System.out.println("Select the time range type? | 1) Start and end | 2) Number of days or weeks from a particular date  | 3) Number of days or weeks to a particular date | 0 to break");
            int timeRange = Integer.parseInt(sc.nextLine());
            if (timeRange==0){break;}
            System.out.println("Select the date group type? | 1) No grouping | 2) Number of groups | 3) Number of days | 0 to break");
            int group = Integer.parseInt(sc.nextLine());
            if (group==0){break;}
            System.out.println("Select the metrics? | 1) Cases | 2) Deaths | 3) People Vaccinated | 0 to break");
            int metrics = Integer.parseInt(sc.nextLine());
            if (metrics==0){break;}
            System.out.println("Select the Result type? | 1) New Total | 2) Up To | 0 to break");
            int resultType = Integer.parseInt(sc.nextLine());
            if (resultType==0){break;}
            System.out.println("Select the Display type? | 1) Tabular | 2) Chart | 0 to break");
            int displayType = Integer.parseInt(sc.nextLine());
            if (displayType==0){break;}
            String continent;
            String country;
            int dateToAdd;
            Date startDate;
            switch (geo) {
                case 1 -> {
                    System.out.println("Continent?");
                    continent = sc.nextLine();
                    data = logicHandling.selectByContinent(continent, data, resultType);
                }
                case 2 -> {
                    System.out.println("Country?");
                    country = sc.nextLine();
                    data = logicHandling.selectByCountry(country, data, resultType);
                }
                default -> {
                    System.out.println("Invalid argument, geographic region");
                    continue;
                }
            }
            switch (timeRange) {
                case 1 -> {
                    System.out.println("Start date? (dd/MM/yyyy)");
                    startDate = format.parse(sc.nextLine());
                    System.out.println("End date? (dd/MM/yyyy)");
                    Date endDate = format.parse(sc.nextLine());
                    data = logicHandling.selectByDateInc(startDate, endDate, data);
                }
                case 2 -> {
                    System.out.println("Start date? (dd/MM/yyyy)");
                    startDate = format.parse(sc.nextLine());
                    System.out.println("Weeks(1) or days(2)");
                    int addOpt = Integer.parseInt(sc.nextLine());
                    dateToAdd = getDateToAdd(sc, addOpt);
                    data = logicHandling.selectByDateFrom(startDate, dateToAdd, data);
                }
                case 3 -> {
                    System.out.println("Start date? (dd/MM/yyyy)");
                    startDate = format.parse(sc.nextLine());
                    System.out.println("Weeks(1) or days(2)");
                    int addOpt = Integer.parseInt(sc.nextLine());
                    dateToAdd = getDateToAdd(sc, addOpt);
                    data = logicHandling.selectByDateTo(startDate, dateToAdd, data);
                }
                default -> {
                    System.out.println("Invalid argument, time range");
                    continue;
                }
            }
            List<Displayable> display = new ArrayList<>();
            switch (group){
                case 1:
                    for (Row r: data){

                        int value=0;
                    String name = format.format(r.getDate());
                        switch (metrics) {
                            case 1 -> value += r.getCases();
                            case 2 -> value += r.getDeaths();
                            case 3 -> value += r.getPeopleVaccinated();
                        }
                        Displayable temp = new Displayable(name, value);
                        display.add(temp);
                    }
                    break;
                case 2:
                    System.out.println("Number of groups");
                    int groups = Integer.parseInt(sc.nextLine());
                    Row[][] grouped = logicHandling.groupByGroups(groups, data);
                    handleGroups(format, metrics, resultType, (List<Displayable>) display, grouped);
                    break;

                case 3:
                    System.out.println("Number of days");
                    int days = Integer.parseInt(sc.nextLine());
                    try {
                        Row[][] groupedByDays = logicHandling.groupByDays(days, data);
                        handleGroups(format, metrics, resultType, (List<Displayable>) display, groupedByDays);
                        break;
                    } catch (InvalidGroup e){
                        e.printStackTrace();
                    }
                default:System.out.println("Invalid argument, groups");
                    continue;

            }
            display.forEach(n -> System.out.println(n.value));
            switch (displayType) {
                case 1 -> tabular(display.toArray(new Displayable[0]));
                case 2 -> chart(display.toArray(new Displayable[0]));
            }
            System.out.println("Continue? 0/1");
            int last = Integer.parseInt(sc.nextLine());
            if (last==0){break;}
        } while (true);
    }

    private static void handleGroups(SimpleDateFormat format, int metrics, int resultType, List<Displayable> display, Row[][] groupedByDays) {
        for (Row[] r: groupedByDays){
            int value=0;
            String name = format.format(r[0].getDate())+"-"+format.format(r[r.length-1].getDate());
            if (resultType == 1){
                for (Row x: r){
                switch (metrics){
                    case 1 -> value += x.getCases();
                    case 2 -> value += x.getDeaths();
                    case 3 -> value += x.getPeopleVaccinated();
                }}}
            else {
                value = switch (metrics) {
                    case 1 -> r[r.length - 1].getCases();
                    case 2 -> r[r.length - 1].getDeaths();
                    case 3 -> r[r.length - 1].getPeopleVaccinated();
                    default -> value;
                };
            }
                Displayable temp = new Displayable(name, value);
                display.add(temp);

        }
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
            System.out.println("Range\t\t\t||\t\tValue\n" + "-----------------------------------------------------");
            for (Displayable i : display) {
            //Check for length of the range group to format the Display
            if (i.range.length() > 7) {
                System.out.print(i.range + "\t\t" + "||\t\t" + i.value + "\n");
                continue;
            }
            //Check for length of the range group to format the Display
            if (i.range.length() > 11) {
                System.out.print(i.range + "\t" + "||\t\t" + i.value + "\n");
                continue;
            }
            //Check for length of the range group to format the Display
            if (i.range.length() > 15) {
                System.out.print(i.range + "||\t\t" + i.value + "\n");
                continue;
            }
            //Print the output
            System.out.print(i.range + "\t\t\t" + "||\t\t" + i.value + "\n");        
    }
    }
    static void chart(Displayable[] display){
        //Chart display

        // Arrange the array base on result value
        Arrays.sort(display, (a, b) -> a.value < b.value ? -1 : 1);
        // Initialize variables
        char[][] matrix = new char[24][80];
        if (display_array.length == 0) {
            System.out.println("The set value is empty. There is nothing to display at the moment. Please select new data or exit: ");
            return 0;
        }
        int max_value = display[(display.length - 1)].value;
        int min_value = display[0].value;
        int row_average = (max_value + min_value) / (matrix.length - 1);
        int top_value = max_value + row_average;
        int group_column = Math.round(matrix[0].length / (display.length) - 1);
        int group_column_holder = group_column;
        int group_column_value = group_column;
        int index = display.length - 1;
        // Assign values to the matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (j == 0) {
                    matrix[i][j] = '|';
                    continue;
                }
                if (i == matrix.length - 1) {
                    matrix[i][j] = '_';
                    continue;
                }
                matrix[i][j] = ' ';
            }
        }
        // Display the chart
        int display_v = display[index].value;
        System.out.println(display_v);
        for (int i = 0; i < matrix.length; i++) {
            if (i == 0) {
                System.out.println("\ty");
                continue;
            }
            System.out.print(top_value + "\t");
            top_value -= row_average;
            for (int j = 0; j < matrix[i].length; j++) {
                int display_value = display[index].value;
                if (display_value >= top_value && display_value <= (top_value + row_average) && j == group_column_value) {
                    matrix[i][j] = '*';
                    index -= 1;
                    if (index == -1) {
                        index = 0;
                    }
                    group_column_value += group_column_holder;
                }
                if (i == matrix.length - 1 && j == group_column) {
                    group_column += group_column_holder;
                    matrix[i][j] = '|';
                }
                if ((j == matrix[i].length - 1) && i == matrix.length - 1) {
                    System.out.println("x");
                    continue;
                }
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        // Display x column
        System.out.print("\t");
        String[] x_column = new String[150];
        int limit = display.length;
        group_column = group_column_holder;
        index = 0;
        //Initialized array to space String
        Arrays.fill(x_column, " ");
        for (int i = 0; i < x_column.length; i++) {
            if (index == limit) {
                break;
            }
            String[] name_array = display[index].range.split("");
            int char_length = name_array.length;
            System.arraycopy(name_array, 0, x_column, group_column, char_length);
            index += 1;
            group_column += group_column_holder;
            if (group_column >= x_column.length) {
                break;
            }
        }
        for (String i : x_column) {
            System.out.print(i);
        }
        System.out.println();
    }
    static class Displayable{
        String range;
        int value;

        public Displayable(String s_range, int s_value) {
            this.range = s_range;
            this.value = s_value;
        }
    }
}
