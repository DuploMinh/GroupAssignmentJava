package covid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Arrays;

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
         Arrays.sort(display, (a, b) -> Integer.parseInt(a.value) < Integer.parseInt(b.value) ? -1 : 1);
         // Initialize variables
         char[][] matrix = new char[24][80];
         int max_value = Integer.parseInt(display[(display.length - 1)].value);
         int min_value = Integer.parseInt(display[0].value);
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
         int display_v = Integer.parseInt(display[index].value);
         System.out.println(display_v);
         for (int i = 0; i < matrix.length; i++) {
             if (i == 0) {
                 System.out.println("\ty");
                 continue;
             }
             System.out.print(top_value + "\t");
             top_value -= row_average;
             for (int j = 0; j < matrix[i].length; j++) {
                 int display_value = Integer.parseInt(display[index].value);
                 if (display_value >= top_value && display_value <= (top_value + row_average) && j == group_column_value) {
                     matrix[i][j] = '*';
                     index -= 1;
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
         for (int i = 0; i < x_column.length; i++) {
             x_column[i] = " ";
         }
         for (int i = 0; i < x_column.length; i++) {
             if (index == limit) {
                 break;
             }
             String[] name_array = display[index].range.split("");
             int char_length = name_array.length;
             for (int j = 0; j < char_length; j++) {
                 x_column[group_column + j] = name_array[j];
             }
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
        String value;

        public Displayable(String s_range, String s_value) {
            this.range = s_range;
            this.value = s_value;
        }
    }
}
