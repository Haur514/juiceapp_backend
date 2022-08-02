package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.HistoryEntity;
import com.example.demo.repository.HistoryRepository;

@Service
public class HistoryService {
    HistoryRepository historyRepository;
    

    /**
     * 全履歴の取得
     * @return
     */
    @Transactional(readOnly = true)
    public List<HistoryEntity> historyList() {
        return historyRepository.findAll();
    }

    /**
     * 履歴の追加
     */
    @Transactional
    public void insertHistory(Long id,String user,String item,int price,Date date){
        HistoryEntity history = new HistoryEntity();
        history.setUser(user);
        history.setId(id);
        history.setItem(item);
        history.setPrice(price);
        history.setDate(date);
        historyRepository.save(history);
    }
}
