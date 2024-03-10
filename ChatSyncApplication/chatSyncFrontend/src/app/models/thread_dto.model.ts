import { ConversationType } from "./enums/conversation_types.enum";

export interface ThreadDto {
    threadId?: string,
    userA?: string,
    userB?: string,
    conversationType?: ConversationType,
    conversationId?: string,
    conversationGroupId?: string,
    conversationName?: string,
    profileImage?: string,
    memberCnt?: number,
    createdAt?: Date,
    updatedAt?: Date
}
