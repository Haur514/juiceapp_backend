package com.example.demo.common.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ManipulateDate {
    // 現在の月から6ヶ月前までの月をリスト形式で返す関数
    public List<Date> getLastSixMonth(){
            Date today = new Date();
    
            Calendar calendar = Calendar.getInstance();
            List<Date> ret = new ArrayList<>();
    
            for(int i = 0; i < 6;i++){
                ret.add(getFirstDate(calendar.getTime()));
                calendar.add(Calendar.MONTH,-1);
            }
    
            ret = ret.stream()
            .sorted(Comparator.comparing(Date::getTime))
            .collect(Collectors.toList());
            return ret;
        }

        // 引数に与えられた月の翌月の月初日を返す
    public Date getNextFirstDate(Date date){
        if (date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        int first = calendar.getActualMinimum(Calendar.DATE);
        calendar.set(Calendar.DATE, first);

        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 000);

        Date ret = new Date();
        ret = calendar.getTime();

        return ret;
    }

    // 月初日を返す
    public Date getFirstDate(Date date) {

        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int first = calendar.getActualMinimum(Calendar.DATE);
        calendar.set(Calendar.DATE, first);

        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 000);

        Date ret = new Date();
        ret = calendar.getTime();

        return ret;
    }


    public static String convertDateToYYYYMM(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String formatYearStr = String.format("%04d", year);
        String formatMonthStr = String.format("%02d", month + 1);
        return formatYearStr + "/" + formatMonthStr;
    }
}
