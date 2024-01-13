package com.chatsyncapp.chatSyncApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "tbl_notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String notificationId;

    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "messageId")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id", referencedColumnName = "id")
    private User receiverUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status")
    private NotificationStatus notificationStatus;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    public Notification() {
    }
}
