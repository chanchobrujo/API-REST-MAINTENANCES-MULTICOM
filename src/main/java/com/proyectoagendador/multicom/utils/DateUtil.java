package com.proyectoagendador.multicom.utils;

import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public
class DateUtil {
    private DateUtil() { }

    public static String formatterDate (LocalDateTime date) {
        Locale local = new Locale("es", "ES");
        DateTimeFormatter dateFormat = ofPattern("EEEE, dd 'de' MMMM 'de' yyyy, 'a las' HH:mm").withLocale(local);
        return date.format(dateFormat);
    }
}
