package com.example.demo.controller;

import java.util.Calendar;
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

        Calendar today = Calendar.getInstance();
        // historyList =
        historyList = historyService.findAllHistory()
                .stream()
                .filter((HistoryEntity historyEntity) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(historyEntity.getDate());
                    // long diff = today.getTimeInMillis() - cal.getTimeInMillis();
                    // Date diff_date = new Date(diff);
                    // Calendar cal_diff = Calendar.getInstance();
                    // cal_diff.setTime(diff_date);
                    // return cal_diff.get(Calendar.YEAR) < 1;
                    return isHowMonthDiffer(cal);
                })
                // .filter((HistoryEntity historyEntity) -> {
                // Calendar cal = Calendar.getInstance();
                // cal.setTime(historyEntity.getDate());
                // return (today.get(Calendar.MONTH) - cal.get(Calendar.MONTH)) < 6;
                // })
                .collect(Collectors.toList());
        // .forEach((HistoryEntity historyEntity) -> {
        // Calendar cal = Calendar.getInstance();
        // cal.setTime(historyEntity.getDate());

        // // YYYY/MM形式で日付を取得
        // String dateYYYYMM = convertDateToYYYYMM(cal);
        // sellingHistoryOfEachMonth.put(dateYYYYMM,
        // sellingHistoryOfEachMonth.getOrDefault(dateYYYYMM, 0) +
        // historyEntity.getPrice());
        // });
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

    private boolean isHowMonthDiffer(Calendar cal) {
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
        String formatMonthStr = String.format("%02d", month+1);
        return formatYearStr + "/" + formatMonthStr;
    }

}