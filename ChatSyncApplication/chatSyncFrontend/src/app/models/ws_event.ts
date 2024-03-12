import { WSNotificationTypes } from "./enums/ws_notification_types.enum";

export class WSEvent {
    public txnId: string;
    public eventType: WSNotificationTypes;
    public eventObject: any;

    constructor(txnId: string, eventType: WSNotificationTypes, eventObject: any) {
        this.txnId = txnId;
        this.eventType = eventType;
        this.eventObject = eventObject;
    }
}