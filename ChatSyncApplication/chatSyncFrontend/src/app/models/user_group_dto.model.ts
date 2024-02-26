export interface UserGroupDto {
    groupId?: string;
    groupMemberId?: string;
    groupName?: string;
    groupProfile?: string;
    isDeleted?: boolean;
    groupMemberRole?: string,
    memberCnt: number,
    createdAt?: Date;
    updatedAt?: Date;
}