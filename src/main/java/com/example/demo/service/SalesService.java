package com.example.demo.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.date.ManipulateDate;
import com.example.demo.entity.SalesEntity;
import com.example.demo.repository.SalesRepository;

@Service
@Transactional
public class SalesService {
    @Autowired
    SalesRepository salesRepository;

    public String updateSales(String name, Date date, int price) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String YYYYMM = ManipulateDate.convertDateToYYYYMM(cal);

        SalesEntity salesEntity = salesRepository.findByUserIdAndDate(name, YYYYMM);
        if(salesEntity == null){
            salesEntity = new SalesEntity();
            salesEntity.setDate(YYYYMM);
            salesEntity.setUserId(name);
            salesEntity.setSales(price);
        }else{
            salesEntity.setSales(salesEntity.getSales()+price);
        }
        salesRepository.save(salesEntity);

        // SalesEntity salesEntity = new SalesEntity();
        // salesEntity.setDate(YYYYMM);
        // salesEntity.setUserId(name);
        // salesEntity.setSales(price);
        // salesRepository.save(salesEntity);

        
        return "success";
    }
}
