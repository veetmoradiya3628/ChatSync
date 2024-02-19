package com.chatsync.chatSyncBackend.model;

import com.chatsync.chatSyncBackend.model.utils.NotificationStatus;
import com.chatsync.chatSyncBackend.model.utils.NotificationTypes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_notifications")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String notificationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationTypes notificationType;

    @Column(name = "notification_content", nullable = false)
    private String notificationContent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status", nullable = false)
    private NotificationStatus notificationStatus;

    @Column(name = "notification_cnt", nullable = false)
    private int notificationCount = 3;

    private LocalDateTime created_at;
    private LocalDateTime consumed_at;
    private LocalDateTime updated_at;
}
