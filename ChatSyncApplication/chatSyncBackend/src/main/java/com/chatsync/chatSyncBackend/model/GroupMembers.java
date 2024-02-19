package com.chatsync.chatSyncBackend.model;

import com.chatsync.chatSyncBackend.model.Group;
import com.chatsync.chatSyncBackend.model.User;
import com.chatsync.chatSyncBackend.model.utils.GroupMemberRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "tbl_group_members")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GroupMembers  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_member_id")
    private String groupMemberId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private GroupMemberRole groupMemberRole;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
