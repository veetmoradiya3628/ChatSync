package com.chatsync.chatSyncBackend.model;

import com.chatsync.chatSyncBackend.model.utils.MessageStatus;
import com.chatsync.chatSyncBackend.model.utils.MessageTypes;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_messages")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id")
    private String messageId;

    private MessageTypes messageType;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private String messageContent;

    private String messageRefUrl;

    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Threads thread;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "receiver_group_id")
    private Group receiverGroup;

    private Boolean isDeleted;

    private MessageStatus messageStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
