package com.chatsyncapp.chatSyncApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "tbl_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String messageId;

    @Column(name = "message_content")
    public String messageContent;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    public MessageTypes messageType;

    @Column(name = "is_deleted")
    public Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_status")
    private MessageStatus messageStatus;

    @ManyToOne(optional = true)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiverUser;

    @ManyToOne(optional = true)
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private Group receiverGroup;

    @Column(name = "sent_at")
    private Timestamp sentAt;

    @Column(name = "delivered_at")
    private Timestamp deliveredAt;

    public Message() {
    }
}
