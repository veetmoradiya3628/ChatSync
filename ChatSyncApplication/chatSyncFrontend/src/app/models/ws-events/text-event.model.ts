export class TextMessageEvent {
    public threadId: string;
    public from: string;
    public to: string;
    public messageContent: string;
    public sentAt: Date;
    public drReq: boolean;

    constructor(threadId: string, from: string, to: string, messageContent: string, sentAt: Date, drReq: boolean) {
        this.threadId = threadId;
        this.from = from;
        this.to = to;
        this.messageContent = messageContent;
        this.sentAt = sentAt;
        this.drReq = drReq;
    }
}