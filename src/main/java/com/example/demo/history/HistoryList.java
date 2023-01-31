package com.example.demo.history;

import java.util.Calendar;
import java.util.List;

import com.example.demo.common.date.ManipulateDate;
import com.example.demo.entity.HistoryEntity;

public class HistoryList {
    final List<HistoryEntity> historyList;
    public HistoryList(List<HistoryEntity> historyEntity){
        this.historyList = historyEntity;
    }

    // 半年以内のリストを取得
    public List<HistoryEntity> getHistoryListWithinHalfYear(){
        return (
            this.historyList
            .stream()
            .filter((HistoryEntity historyEntity) -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime(historyEntity.getDate());
                return ManipulateDate.isWithinHalfOfYear(cal,Calendar.getInstance());
            })
            .toList()
        );
    }
}
