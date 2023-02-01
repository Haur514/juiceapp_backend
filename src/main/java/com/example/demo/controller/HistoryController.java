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
import com.example.demo.history.HistoryList;
import com.example.demo.history.selling.SellingOfEachMonth;
import com.example.demo.history.selling.SellingOfEachPersonWithinHalfYear;
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
        SellingOfEachMonth sellingOfEachMonth = new SellingOfEachMonth(
                new HistoryList(historyService.findAllHistory()));
        return sellingOfEachMonth.getSellingAmountOfEachMonthAsJson();
    }

    // 過去最大6ヶ月分の料金を各月ごとに返す
    @RequestMapping("/history/billingamount")
    public String getMemberBillingAmount(
            @RequestParam(name = "name") String name) {
        // 半年以内におけるメンバーnameの購入金額を取得
        HistoryList historyList = new HistoryList(historyService.findAllHistory());
        SellingOfEachPersonWithinHalfYear sellingOfEachPersonWithinHalfYear = new SellingOfEachPersonWithinHalfYear(historyList,name);
        return sellingOfEachPersonWithinHalfYear.getSellingOfEachPersonWithinHalfYearAsJson();
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