package ci.nkagou.closedloop.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public  class DateConvert {

    public static String getStringDate(LocalDateTime dateTime){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String out = dateTime.format(formatter);
        return out;
    }
}
