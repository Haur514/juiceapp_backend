package com.example.demo.history.selling;

import java.util.Calendar;
import java.util.Map;

import com.example.demo.common.date.ManipulateDate;
import com.example.demo.history.HistoryList;
import com.google.gson.Gson;

public class SellingOfEachPersonWithinHalfYear {

    // ある個人"name"の売り上げを，
    // {YYYYMM : ¥amount} で記録
    String memberName;
    Map<String, Integer> sellingOfEachPersonWithinHalfYear;

    HistoryList historyList;

    public SellingOfEachPersonWithinHalfYear(HistoryList historyList,String memberName){
        this.historyList = historyList;
        this.memberName = memberName;        
        setSellingOfEachPersonWithinHalfYear();
    }

    
    public void setSellingOfEachPersonWithinHalfYear(){
        initSellingHistoryOfEachMonth(sellingOfEachPersonWithinHalfYear);
        HistoryList historyListOfMemberWithinHalfYear = 
        historyList.getHistoryListOfMemberWithinHalfYear(memberName);

        historyListOfMemberWithinHalfYear
                .getHistoryList()
                .stream()
                .forEach((historyEntity) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(historyEntity.getDate());

                    // YYYY/MM形式で日付を取得
                    String dateYYYYMM = ManipulateDate.convertDateToYYYYMM(cal);
                    sellingOfEachPersonWithinHalfYear.put(dateYYYYMM,
                            sellingOfEachPersonWithinHalfYear.getOrDefault(dateYYYYMM, 0) + historyEntity.getPrice());
                });
    }

    // 過去6ヶ月分の料金をJson形式で返す
    public String getSellingOfEachPersonWithinHalfYearAsJson(){
        return new Gson().toJson(this.sellingOfEachPersonWithinHalfYear);
    }


    // を初期化する
    private void initSellingHistoryOfEachMonth(Map<String, Integer> sellingHistoryOfEachMonth) {
        for (String YYYYMM : ManipulateDate.getMonthWithinHalfYearAsStringYYYYMM(Calendar.getInstance())) {
            sellingHistoryOfEachMonth.put(YYYYMM, 0);
        }
    }
}
