package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.HistoryEntity;
import com.example.demo.repository.HistoryRepository;

@Controller
@Transactional
public class HistoryService {
    @Autowired
    HistoryRepository historyRepository;

    public List<HistoryEntity> findAllHistory(){
        return historyRepository.findAll();
    }

    public List<HistoryEntity> findByName(String name){
        return historyRepository.findByName(name);
    }

    public String removeHistory(int id,String name){
        try{
            HistoryEntity historyEntityFoundById = historyRepository.getReferenceById(id);
            if(historyEntityFoundById.getName().equals(name)){
                historyRepository.deleteById(id);
                return "success";
            }else{
                return "false";
            }
        }catch(Exception e){
            return "failed";
        }
    }


    public String insertHistory(String name, String item, int price) {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setName(name);
        historyEntity.setItem(item);
        historyEntity.setPrice(price);
        historyEntity.setDate(new java.sql.Date(new java.util.Date().getTime()));
        historyRepository.save(historyEntity);
        return "success";
    }
}
