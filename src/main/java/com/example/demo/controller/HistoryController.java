package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.date.ManipulateDate;
import com.example.demo.entity.HistoryEntity;
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
        List<HistoryEntity> historyList;
        Gson gson = new Gson();
        Map<String, Integer> sellingHistoryOfEachMonth = new HashMap<>();
        initSellingHistoryOfEachMonth(sellingHistoryOfEachMonth);

        Calendar today = Calendar.getInstance();
        // historyList =
        historyList = historyService.findAllHistory()
                .stream()
                .filter((HistoryEntity historyEntity) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(historyEntity.getDate());
                    return isWithinHalfOfYear(cal);
                })
                .collect(Collectors.toList());
        historyList.stream().forEach((historyEntity) -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(historyEntity.getDate());

            // YYYY/MM形式で日付を取得
            String dateYYYYMM = convertDateToYYYYMM(cal);
            sellingHistoryOfEachMonth.put(dateYYYYMM,
                    sellingHistoryOfEachMonth.getOrDefault(dateYYYYMM, 0) + historyEntity.getPrice());
        });
        return gson.toJson(sellingHistoryOfEachMonth);
    }

    // @RequestMapping("/history/juice/eachmonth")
    // public String getJuiceHistoryOfEachMonth() {
    // List<HistoryEntity> historyList;
    // Gson gson = new Gson();
    // Map<String, Integer> sellingHistoryOfEachMonth = new HashMap<>();
    // initSellingHistoryOfEachMonth(sellingHistoryOfEachMonth);

    // Calendar today = Calendar.getInstance();
    // // historyList =
    // historyList = historyService.findAllHistory()
    // .stream()
    // .filter((HistoryEntity historyEntity) -> {
    // Calendar cal = Calendar.getInstance();
    // cal.setTime(historyEntity.getDate());
    // return isWithinHalfOfYear(cal);
    // })
    // .collect(Collectors.toList());
    // historyList.stream().forEach((historyEntity) -> {
    // Calendar cal = Calendar.getInstance();
    // cal.setTime(historyEntity.getDate());

    // // YYYY/MM形式で日付を取得
    // String dateYYYYMM = convertDateToYYYYMM(cal);
    // sellingHistoryOfEachMonth.put(dateYYYYMM,
    // sellingHistoryOfEachMonth.getOrDefault(dateYYYYMM, 0) +
    // historyEntity.getPrice());
    // });
    // return gson.toJson(sellingHistoryOfEachMonth);
    // }

    // sellingHistoryOfEachMonthを初期化する
    private void initSellingHistoryOfEachMonth(Map<String, Integer> sellingHistoryOfEachMonth) {
        for (String YYYYMM : getMonthWithinHalfYearAsStringYYYYMM()) {
            sellingHistoryOfEachMonth.put(YYYYMM, 0);
        }
    }

    // 現在の時刻から半年以内の月をYYYY/MMの形で6つ列挙する
    private List<String> getMonthWithinHalfYearAsStringYYYYMM() {
        List<String> ret = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 6; i++) {
            ret.add(convertDateToYYYYMM(cal));
            cal.add(Calendar.MONTH, -1);
        }
        return ret;
    }

    // historyEntityに記録されたDateが現在から半年以内か判定する
    private boolean isTheHistoryRegisteredWithinHalfYear(HistoryEntity historyEntity) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(historyEntity.getDate());
        return isWithinHalfOfYear(cal);
    }

    // ある日付が現在から半年以内かを判定する
    private boolean isWithinHalfOfYear(Calendar cal) {
        Calendar today = Calendar.getInstance();
        if (today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
            if (today.get(Calendar.MONTH) - cal.get(Calendar.MONTH) < 6) {
            }
            {
                return true;
            }
        } else if (today.get(Calendar.YEAR) - cal.get(Calendar.YEAR) == 1) {
            if (today.get(Calendar.MONTH) - 6 + 12 < cal.get(Calendar.MONTH)) {
                return true;
            }
        }
        return false;
    }

    private String convertDateToYYYYMM(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String formatYearStr = String.format("%04d", year);
        String formatMonthStr = String.format("%02d", month + 1);
        return formatYearStr + "/" + formatMonthStr;
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
                    return isTheHistoryRegisteredWithinHalfYear(historyEntity);
                })
                .forEach((historyEntity) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(historyEntity.getDate());

                    // YYYY/MM形式で日付を取得
                    String dateYYYYMM = convertDateToYYYYMM(cal);
                    billingAmountForEachMonth.put(dateYYYYMM,
                            billingAmountForEachMonth.getOrDefault(dateYYYYMM, 0) + historyEntity.getPrice());
                });
        return new Gson().toJson(billingAmountForEachMonth);
    }

    // 年，月を指定することで，構成員全員の使用した金額を出力する．
    @RequestMapping("/history/billingamount/allmember")
    public String getBillingAmountAllMember(){
        Map<String,Map<String,Integer>> ret = new HashMap<>();
        for(Date date: new ManipulateDate().getLastSixMonth()){
            Map<String,Integer> billingAmountOfAMonth = new HashMap<>();
            billingAmountOfAMonth = historyService.getBillingAmountOfAMonth(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            ret.put(convertDateToYYYYMM(cal),billingAmountOfAMonth);
        }
        return new Gson().toJson(ret);
    }

}