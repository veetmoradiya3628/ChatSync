package com.chatsync.chatSyncBackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_user_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userRoleId;

    // user
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne
    private Role role;
}

