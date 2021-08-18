package covid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class logicHandling {
    public static List<Row> selectByDateInc(Date startDate, Date endDate, List<Row> input) {
        return input.stream().filter(p -> p.isInDateRange(startDate, endDate)).collect(Collectors.toList());
    }

    public static List<Row> selectByDateFrom(Date startDate, int number, List<Row> input) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DAY_OF_MONTH, number);
        return selectByDateInc(startDate, c.getTime(), input);
    }

    public static List<Row> selectByDateTo(Date startDate, int number, List<Row> input) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        number *= -1;
        c.add(Calendar.DAY_OF_MONTH, number);
        return selectByDateInc(c.getTime(), startDate, input);
    }

    public static List<Row> selectByCountry(String country, List<Row> input) {
        return input.stream().filter(p -> p.getLocation().equals(country)).collect(Collectors.toList());
    }

    public static List<Row> selectByContinent(String country, List<Row> input) {
        return input.stream().filter(p -> p.getContinent().equals(country)).collect(Collectors.toList());
    }

    public static Row[][] groupByGroups(int groups, List<Row> list) {
        int length = list.size() / groups;
        int index = 0;
        Row[][] ret = new Row[groups][length + 1];
        for (int i = 0; i < groups; i++) {
            if (i <list.size() % groups) {
                for (int x = 0; x < length + 1; x++) {
                    ret[i][x] = list.get(index);
                    index++;
                }
            } else {
                for (int x = 0; x < length; x++) {
                    ret[i][x] = list.get(index);
                    index++;
                }
            }
        }
        return ret;
    }
    public static Row[][] groupByDays(int groups, List<Row> list) {
        int length = list.size()/groups;
        int index = 0;
        if (list.size()/groups!=0){
            throw new InvalidGroup("Data could not be split into groups with this number of days");
        }
        Row[][] ret = new Row[length][groups];
        for (int i =0; i<length;i++){
            for (int x = 0; x<groups; x++){
                ret[i][x] = list.get(index);
                index++;
            }
        }
        return ret;
    }
}
class InvalidGroup
        extends RuntimeException {
    public InvalidGroup(String errorMessage) {
        super(errorMessage);
    }
}
