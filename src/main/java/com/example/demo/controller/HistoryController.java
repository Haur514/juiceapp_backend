package com.example.demo.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.date.ManipulateDate;
import com.example.demo.entity.HistoryEntity;
import com.example.demo.history.selling.SellingOfEachMonth;
import com.example.demo.service.HistoryService;
import com.google.gson.Gson;

@RestController
@EnableAutoConfiguration
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @PostMapping
    @RequestMapping("/history/add")
    public String insertHistory(
            @RequestParam("name") String name,
            @RequestParam("item") String item,
            @RequestParam("price") String price) {
        int p = Integer.parseInt(price);
        return historyService.insertHistory(name, item, p);
    }

    @PostMapping
    @RequestMapping("/history/delete")
    public String cancelHistory(@RequestParam("name") String name, @RequestParam("id") String id) {
        return historyService.removeHistory(Integer.parseInt(id), name);
    }
    
    @RequestMapping("/history")
    public String getHistory(@RequestParam(name = "name", defaultValue = "") String name) {
        List<HistoryEntity> historyList;
        Gson gson = new Gson();
        if (name.equals("")) {
            historyList = historyService.findAllHistory();
        } else {
            historyList = historyService.findByName(name);
        }
        return gson.toJson(historyList);
    }


    @RequestMapping("/history/eachmonth")
    public String getHistoryOfEachMonth() {
        SellingOfEachMonth sellingOfEachMonth = new SellingOfEachMonth(historyService);
        return sellingOfEachMonth.getSellingAmountOfEachMonthAsJson();
    }

    // sellingHistoryOfEachMonthを初期化する
    private void initSellingHistoryOfEachMonth(Map<String, Integer> sellingHistoryOfEachMonth) {
        for (String YYYYMM : ManipulateDate.getMonthWithinHalfYearAsStringYYYYMM(Calendar.getInstance())) {
            sellingHistoryOfEachMonth.put(YYYYMM, 0);
        }
    }

    // 過去最大6ヶ月分の料金を各月ごとに返す
    @RequestMapping("/history/billingamount")
    public String getMemberBillingAmount(
            @RequestParam(name = "name") String name) {

        Map<String, Integer> billingAmountForEachMonth = new HashMap<>();
        initSellingHistoryOfEachMonth(billingAmountForEachMonth);

        List<HistoryEntity> historyEntities = historyService.findByName(name);

        historyEntities.stream()
        .filter((historyEntity) -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(historyEntity.getDate());
            return ManipulateDate.isWithinHalfOfYear(cal,Calendar.getInstance());
        })
        .forEach((historyEntity) -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(historyEntity.getDate());

            // YYYY/MM形式で日付を取得
            String dateYYYYMM = ManipulateDate.convertDateToYYYYMM(cal);
            billingAmountForEachMonth.put(dateYYYYMM,
                    billingAmountForEachMonth.getOrDefault(dateYYYYMM, 0) + historyEntity.getPrice());
        });
        return new Gson().toJson(billingAmountForEachMonth);
    }

    // 年，月を指定することで，構成員全員の使用した金額を出力する．
    @RequestMapping("/history/billingamount/allmember")
    public String getBillingAmountAllMember() {
        Map<String, Map<String, Integer>> ret = new HashMap<>();
        for (Date date : ManipulateDate.getLastSixMonth(new Date())) {
            Map<String, Integer> billingAmountOfAMonth = new HashMap<>();
            billingAmountOfAMonth = historyService.getBillingAmountOfAMonth(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            ret.put(ManipulateDate.convertDateToYYYYMM(cal), billingAmountOfAMonth);
        }
        return new Gson().toJson(ret);
    }
}