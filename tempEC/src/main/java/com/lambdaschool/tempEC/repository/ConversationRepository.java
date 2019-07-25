package com.lambdaschool.tempEC.repository;

import com.lambdaschool.tempEC.models.Conversation;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {
}
