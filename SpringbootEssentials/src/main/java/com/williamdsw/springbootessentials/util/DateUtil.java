package com.williamdsw.springbootessentials.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 * @author William
 */

@Component
public class DateUtil
{
    public String formatLocalDateTimeToDatabaseStyle (LocalDateTime localDateTime)
    {
        return DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm:ss").format (localDateTime);
    }
}