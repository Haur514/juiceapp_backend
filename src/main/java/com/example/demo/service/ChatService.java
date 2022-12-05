package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.ChatEntity;
import com.example.demo.repository.ChatRepository;

@Service
@Transactional
public class ChatService {
    @Autowired
    ChatRepository chatRepository;

    public List<ChatEntity> findAllChat(){
        return chatRepository.findAll();
    }

    public String removeChat(int id){
        try{
            // ChatEntity chatEntityFoundById = chatRepository.getReferenceById(id);
            chatRepository.deleteById(id);
            return "success";
        }catch(Exception e){
            return "failed";
        }
    }


    public String insertChat(String message) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setMessage(message);
        chatRepository.save(chatEntity);
        return "success";
    }
}
