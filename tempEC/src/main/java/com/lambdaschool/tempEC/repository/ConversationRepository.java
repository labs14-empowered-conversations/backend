package com.lambdaschool.tempEC.repository;

import com.lambdaschool.tempEC.models.Conversation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE from conversation", nativeQuery = true)
    void deleteConvotask();
}
