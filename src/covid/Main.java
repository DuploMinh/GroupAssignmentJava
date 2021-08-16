package covid;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        List<Row> test = csvReader.readCSV("src/covid/covid-data.csv");
        System.out.println(test);
    }
}
