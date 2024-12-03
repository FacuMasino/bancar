package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateRangeFormatter 
{
    public static List<String> generateFormattedMonths(LocalDate startDate, LocalDate endDate) {
        List<String> formattedMonths = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yy").withLocale(java.util.Locale.ENGLISH);
        
        //Fuerzo el 1er dia de mes para la comparativa posterior...
        LocalDate currentDate = startDate.withDayOfMonth(1); 
        endDate = endDate.withDayOfMonth(1); 
        
        while (!currentDate.isAfter(endDate)) {
            formattedMonths.add(currentDate.format(formatter).toUpperCase());
            currentDate = currentDate.plusMonths(1); // Avanzar al siguiente mes
        }       
        return formattedMonths;
    } 
    
    public static List<String> generateFormattedDays(LocalDate startDate, LocalDate endDate)
    {
        List<String> formattedDays = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        
        LocalDate currentDate = startDate;
        /*
         * //Fuerzo el 1er dia de mes para la comparativa posterior...
        LocalDate currentDate = startDate.withDayOfMonth(1); 
        endDate = endDate.withDayOfMonth(1); 
        */
        while (!currentDate.isAfter(endDate)) {
            formattedDays.add(currentDate.format(formatter));
            currentDate = currentDate.plusDays(1); // Avanzar al siguiente dia
        }       
        return formattedDays;
    } 
}
