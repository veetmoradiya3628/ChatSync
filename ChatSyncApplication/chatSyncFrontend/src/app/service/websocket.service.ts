import { Injectable, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import { AuthService } from './auth.service';
import { MessageDto } from '../models/message_dto.model';
import { WSEvent } from '../models/ws_event';
import { UserChatCommonServiceService } from '../user/user-chat/user-chat-common-service.service';
import { WSNotificationTypes } from '../models/enums/ws_notification_types.enum';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  ws: any;
  private user_email = '';
  connectRef: any;

  private GLOBAL_TOPIC = '/topic/global';
  private PRIVATE_TOPIC = '/topic/private/';
  private SEND_MESSAGE_URL = '/app/message';

  constructor(private _authService: AuthService, public _chatCommonService: UserChatCommonServiceService) {
    console.log(this._authService.getUserEmail());
    this.user_email = this._authService.getUserEmail() || '';
  }

  connect(): Promise<void> {
    return new Promise<void>((resolve) => {
      let socket = new WebSocket("ws://localhost:8080/ws");
      this.ws = Stomp.over(socket);
      this.connectRef = this;

      const headers = {
        'username': this.user_email,
      };

      console.log(headers);
      this.ws.connect(headers, (frame: any) => {
        this.connectRef.ws.subscribe("/errors", function (message: any) {
          alert("Error " + message.body);
        });

        this.subscribeToTopic();
        this.subscribeToUserTopic();

      }, function (error: any) {
        alert("STOMP error " + error);
      });
    });
  }

  subscribeToTopic() {
    this.connectRef.ws.subscribe(this.GLOBAL_TOPIC, function (message: any) {
      console.log(message)
    });
  }

  subscribeToUserTopic() {
    this.connectRef.ws.subscribe(this.PRIVATE_TOPIC + this.user_email, (message: any) => {
      console.log(message.body);
      let respObject = JSON.parse(message.body);
      this.processOneToOneReceivedWSMessage(respObject);
    });
  }

  processOneToOneReceivedWSMessage(respObject: any) {
    let wsNotificationType: WSNotificationTypes = respObject["eventType"];
    switch (wsNotificationType) {
      case WSNotificationTypes.ONE_TO_ONE_SENT_TEXT_CONFIRM:
        this.processOneToOneTextMessageConfirm(respObject.eventObject);
        break;
      case WSNotificationTypes.RECEIVE_ONE_TO_ONE_TEXT_MESSAGE:
        this.processOneToOneReceivedMessage(respObject.eventObject);
        break;
      case WSNotificationTypes.RECEIVE_GROUP_TEXT_MESSAGE_CONFIRM:
        this.processGroupMessageConfirm(respObject.eventObject);
        break;
      case WSNotificationTypes.RECEIVE_GROUP_TEXT_MESSAGE:
        this.processGroupReceivedMessage(respObject.eventObject);
        break;
    }
  }

  processOneToOneTextMessageConfirm(eventObj: any) {
    console.log(`processOneToOneTextMessageConfirm :: start`);
    console.log(eventObj)
    this._chatCommonService.addMessageToThread(eventObj.threadId, eventObj);
  }

  processGroupMessageConfirm(eventObj: any){
    console.log(`processGroupMessageConfirm :: start`);
    console.log(eventObj)
    this._chatCommonService.addMessageToThread(eventObj.threadId, eventObj);
  }

  processOneToOneReceivedMessage(eventObj: any) {
    console.log(`processOneToOneReceivedMessage :: start`);
    console.log(eventObj);
    this._chatCommonService.addMessageToThread(eventObj.threadId, eventObj);
  }

  processGroupReceivedMessage(eventObj: any) {
    console.log(`processGroupReceivedMessage :: start`);
    console.log(eventObj);
    this._chatCommonService.addMessageToThread(eventObj.threadId, eventObj);
  }

  sentMessage(message: WSEvent) {
    this.ws.send(this.SEND_MESSAGE_URL, {}, JSON.stringify(message));
  }

  disconnect() {
    if (this.ws != null) {
      this.ws.ws.close();
    }
    console.log("Disconnected");
  }
}
