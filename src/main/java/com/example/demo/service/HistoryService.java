package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.HistoryEntity;
import com.example.demo.repository.HistoryRepository;

@Service
@Transactional
public class HistoryService {
    @Autowired
    HistoryRepository historyRepository;

    public List<HistoryEntity> findAllHistory(){
        return historyRepository.findAll();
    }

    // public List<HistoryEntity> findAllJuiceHistory(){
    //     return historyRepository.findAll()
    //     .stream()
    //     .filter((HistoryEntity historyEntity) -> {
    //         if(historyEntity.get)
    //         return true;
    //     })
    //     .collect(Collectors.toList(());
    // }

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

    public boolean isRegistered(int id, String name) {
        try{
            HistoryEntity historyEntityFoundById = historyRepository.getReferenceById(id);
            if(historyEntityFoundById.getName().equals(name)){
                return true;
            }
            return false;
        }catch(Exception e){
            return false;
        }
    }
}
