package br.com.culture.manager.cultureManager.domain.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class LocalDateTimeMappers {
    private static Locale userLocale;
    public static String toString(LocalDateTime date) {
        if(userLocale != Locale.getDefault()){
            userLocale = Locale.getDefault();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(userLocale);

        return date.format(formatter);
    }
}
