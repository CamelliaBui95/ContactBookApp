package fr.btn.contactsbook.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String format(LocalDate date) {
        if(date == null)
            return null;

        return DATE_FORMATTER.format(date);
    }

    public static LocalDate parse(String dateStr) {
        try {
            return DATE_FORMATTER.parse(dateStr, LocalDate::from);
        } catch(DateTimeParseException e) {
            return null;
        }
    }

    public static boolean isValidDate(String dateStr) {
        return parse(dateStr) != null;
    }


}
