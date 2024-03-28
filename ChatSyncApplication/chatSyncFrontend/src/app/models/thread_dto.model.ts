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
    pendingToReadMessageCnt?: number,
    isReadPending?: boolean,
    lastLoadedPageNumber?: number,
    isLastPage?: boolean,
    createdAt?: Date,
    updatedAt?: Date
}

const defaultThreadDto: Partial<ThreadDto> = {
  memberCnt: 0,
  pendingToReadMessageCnt: 0,
  isReadPending: false,
  lastLoadedPageNumber: 0,
  isLastPage: false
};
