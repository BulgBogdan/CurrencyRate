package currencyRate.controller.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class HistoryUtil {

    public static String getDate(Date date) {
        String selectDate;
        if (Objects.isNull(date)) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            selectDate = dateFormat.format(LocalDate.now());
        } else {
            selectDate = String.valueOf(date);
        }
        return selectDate;
    }
}
