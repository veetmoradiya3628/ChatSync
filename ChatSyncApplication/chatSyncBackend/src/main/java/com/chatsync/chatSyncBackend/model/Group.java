package com.chatsync.chatSyncBackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tbl_group")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String groupId;

    private String groupName;
    private String groupProfileImage;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToMany
    @JoinTable(
            name = "tbl_group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
