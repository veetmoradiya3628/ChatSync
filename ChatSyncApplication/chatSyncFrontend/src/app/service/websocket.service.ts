import { Injectable, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import { AuthService } from './auth.service';
import { MessageDto } from '../models/message_dto.model';
import { WSEvent } from '../models/ws_event';
import { UserChatCommonServiceService } from '../user/user-chat/user-chat-common-service.service';

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
    this.connectRef.ws.subscribe(this.PRIVATE_TOPIC + this.user_email,  (message: any) => {
      console.log(message.body);
      let eventObject = JSON.parse(message.body);
      this.processOneToOneReceivedMessage(eventObject);
    });
  }

  processOneToOneReceivedMessage(eventObject: any){
    if (eventObject.messageType === 'ONE_TO_ONE_TEXT') {
      console.log(`processing one to one event`);
      this._chatCommonService.addMessageToThread(eventObject.threadId, eventObject);
    }
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
