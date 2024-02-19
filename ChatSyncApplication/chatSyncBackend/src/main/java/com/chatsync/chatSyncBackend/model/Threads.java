package com.chatsync.chatSyncBackend.model;

import com.chatsync.chatSyncBackend.model.utils.ConversationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_threads")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Threads {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "thread_id")
    private String threadId;

    private ConversationType conversationType;

    @Column(name = "conv_id")
    private String conversationId;

    @ManyToOne
    @JoinColumn(name = "conv_group_id")
    private Group conversationGroupId;

    @ManyToOne
    @JoinColumn(name = "last_message_id")
    private Messages message;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
