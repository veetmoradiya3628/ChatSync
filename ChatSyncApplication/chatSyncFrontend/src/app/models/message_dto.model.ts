import { MessageDirection } from "./enums/message_direction.enum";
import { MessageStatus } from "./enums/message_status.enum";
import { MessageTypes } from "./enums/message_types.enum";

export interface MessageDto {
    messageId?: string,
    messageType?: MessageTypes,
    senderId: string,
    messageContent?: string,
    messageRefUrl?: string,
    threadId?: string,
    receiverId?: string,
    receiverGroupId?: string,
    messageDirection?: MessageDirection,
    messageStatus?: MessageStatus,
    createdAt?: Date,
    updatedAt?: Date
}


