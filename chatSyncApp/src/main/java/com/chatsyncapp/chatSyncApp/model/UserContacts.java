package com.chatsyncapp.chatSyncApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tbl_user_contacts")
@Setter
@Getter
@AllArgsConstructor
public class UserContacts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_contact_id")
    public String userContactId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User userId;

    @ManyToOne
    @JoinColumn(name = "contact_user_id", referencedColumnName = "id")
    public User contactUserId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UserContacts(){}

    public UserContacts(User user,
                        User contactUser){
        this.userId = user;
        this.contactUserId = contactUser;
    }
}
