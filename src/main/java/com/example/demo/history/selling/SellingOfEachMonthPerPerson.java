package com.example.demo.history.selling;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.common.date.ManipulateDate;
import com.example.demo.history.HistoryList;

// 指定した月に各構成員が利用した金額を取得する
public class SellingOfEachMonthPerPerson {
    Map<String, Integer> sellingOfEachMonthPerPerson = new HashMap<>();
    
    SellingOfEachMonthPerPerson(HistoryList historyList){
        setSellingOfEachMonthPerPerson(historyList,Calendar.getInstance());
    }

    private void setSellingOfEachMonthPerPerson(HistoryList historyList,Calendar cal){
        List<String> monthsWithinHalfYearAsStringYYYYMM = ManipulateDate.getMonthWithinHalfYearAsStringYYYYMM(cal);

        for(String month : monthsWithinHalfYearAsStringYYYYMM){
            String[] split = month.split("/");
            int YYYY = Integer.parseInt(split[0]);
            int MM = Integer.parseInt(split[1]);
            HistoryList historyListOfSpecifiedMonth = historyList.getHistoryListOfSpecifiedMonth(YYYY, MM);

            
        }
    }
}
