package br.com.culture.manager.cultureManager.domain.mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class LocalDateMappers {

    public static String toString(LocalDate date) {
        Locale userLocale = Locale.getDefault();

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(userLocale);

        return date.format(formatter);
    }
}
