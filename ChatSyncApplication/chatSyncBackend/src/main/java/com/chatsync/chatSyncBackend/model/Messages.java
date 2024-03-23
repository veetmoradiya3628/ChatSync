package com.chatsync.chatSyncBackend.model;

import com.chatsync.chatSyncBackend.model.utils.MessageStatus;
import com.chatsync.chatSyncBackend.model.utils.MessageTypes;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class Messages {

    public static String LOG_TAG = "Messages";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id")
    private String messageId;

    @Enumerated(EnumType.STRING)
    private MessageTypes messageType;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private String messageContent;

    private String messageRefUrl;

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

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PostPersist
    public void afterMessageSaved(){
        log.info(LOG_TAG + " afterMessageSaved called with : " + this);
    }
}
