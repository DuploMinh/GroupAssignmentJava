package covid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class logicHandling {
    public static List<Row> selectByDateInc(Date startDate, Date endDate, List<Row> input){
        return input.stream().filter(p -> p.isInDateRange(startDate, endDate)).collect(Collectors.toList());
    }
    public static List<Row> selectByCountry(String country, List<Row> input){
        return input.stream().filter(p -> p.getLocation().equals(country)).collect(Collectors.toList());
    }
    public static List<Row> selectByContinent(String country, List<Row> input){
        return input.stream().filter(p -> p.getContinent().equals(country)).collect(Collectors.toList());}
}
