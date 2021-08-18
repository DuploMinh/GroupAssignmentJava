package covid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParseException {
	// write your code here
        List<Row> test = csvReader.readCSV("src/covid/covid-data.csv");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = format.parse("2/29/2020");
        Date endDate = format.parse("2/25/2020");
        List<Row> test1 = logicHandling.selectByDateTo(startDate,3,test);
        System.out.println(test1);
    }
}
