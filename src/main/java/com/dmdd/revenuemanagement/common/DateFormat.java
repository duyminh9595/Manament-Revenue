package com.dmdd.revenuemanagement.common;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Service
public class DateFormat {

    public Date GetDateNow()
    {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(new Date().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date ConvertStringToDate(String dateOfBirth)
    {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
