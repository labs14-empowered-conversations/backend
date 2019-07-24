package com.lambdaschool.tempEC;

import com.lambdaschool.tempEC.models.Conversation;
import com.lambdaschool.tempEC.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    ConversationService convoService;


    @Override
    public void run(String[] args) throws Exception
    {
        Conversation c1 = new Conversation("318-112-3827", "Jane", "318-321-4823");
        convoService.save(c1);

        Conversation c2 = new Conversation("318-421-5782", "Johnny", "281-382-2818");
        convoService.save(c2);

        Conversation c3 = new Conversation( "318-637-3817", "Bobby", "318-271-4872")

    }
}