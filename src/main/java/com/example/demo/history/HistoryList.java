package com.example.demo.history;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.example.demo.common.date.ManipulateDate;
import com.example.demo.entity.HistoryEntity;

public class HistoryList {
    final List<HistoryEntity> historyList;
    public HistoryList(List<HistoryEntity> historyEntity){
        this.historyList = historyEntity;
    }

    public List<HistoryEntity> getHistoryList(){
        return Collections.unmodifiableList(this.historyList);
    }

    
    // 半年以内のリストを取得
    public HistoryList getHistoryListWithinHalfYear(){
        return (
            new HistoryList(
                this.historyList
                .stream()
                .filter((HistoryEntity historyEntity) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(historyEntity.getDate());
                    return ManipulateDate.isWithinHalfOfYear(cal,Calendar.getInstance());
                })
                .toList()
            )
        );
    }

    // あるメンバーのリストを取得
    public HistoryList getHistoryListOfMember(String memberName){
        return (
            new HistoryList(
                this.historyList
                .stream()
                .filter((HistoryEntity historyEntity) -> {
                    return historyEntity.getName().equals(memberName);
                })
                .toList()
            )
        );
    }

    // あるメンバーの半年以内の購入金額を取得
    public HistoryList getHistoryListOfMemberWithinHalfYear(String memberName){
        return (
            getHistoryListOfMember(memberName)
            .getHistoryListWithinHalfYear()
        );
    }
}
