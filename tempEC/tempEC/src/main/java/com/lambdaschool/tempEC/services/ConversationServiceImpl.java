package com.lambdaschool.tempEC.services;

import com.lambdaschool.tempEC.exceptions.ResourceNotFoundException;
import com.lambdaschool.tempEC.models.Conversation;
import com.lambdaschool.tempEC.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "convoService")
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    private ConversationRepository restrepos;

    @Override
    public Conversation save(Conversation conversation) {
        Conversation newConvo = new Conversation();
        if(conversation.getFfname() != null) {
            newConvo.setFfname(conversation.getFfname());
        }
        if(conversation.getFfnumber() != null) {
            newConvo.setFfnumber(conversation.getFfnumber());
        }
        if(conversation.getSurvivornumber() != null) {
            newConvo.setSurvivornumber(conversation.getSurvivornumber());
        }
        return restrepos.save(newConvo);
    }

    @Override
    public Conversation findById(long id) {
        return restrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Conversation with that ID not found"));
    }

    @Override
    public void delete(long id) {
        restrepos.deleteById(id);
    }

    @Override
    public List<Conversation> findAll() {
        ArrayList<Conversation> list = new ArrayList<>();
        restrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
}