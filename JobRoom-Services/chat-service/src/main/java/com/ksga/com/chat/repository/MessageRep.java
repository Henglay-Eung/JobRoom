package com.ksga.com.chat.repository;

import com.ksga.com.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRep extends JpaRepository<Chat, Integer>
{
    @Query("SELECT m FROM Chat as m WHERE m.senderId=:senderNameId AND m.receiverId=:receiverNameId")
    Chat findAllMessageByRoom(@Param("senderNameId") int senderNameId, @Param("receiverNameId") int receiverNameId);

    Chat getChatByRoomNumber(String roomNumber);

    @Query("SELECT c.receiverId FROM Chat AS c WHERE c.senderId = :senderId ")
    List<Integer> getReceiverIdBySenderCmp(int senderId);

    @Query("SELECT c.senderId FROM Chat AS c WHERE c.receiverId = :receiverId ")
    List<Integer> getReceiverIdBySenderEmp(int receiverId);


}
