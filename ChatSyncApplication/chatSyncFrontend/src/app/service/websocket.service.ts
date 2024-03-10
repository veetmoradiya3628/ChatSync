import { Injectable, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import { AuthService } from './auth.service';
import { MessageDto } from '../models/message_dto.model';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  ws: any;
  private user_email = '';
  connectRef: any;
  private SEND_MESSAGE_URL = '/app/message';

  constructor(private _authService: AuthService) {
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

        this.subscribeToTopic("/topic/global");
        this.subscribeToTopic("/topic/private/" + this.user_email);

      }, function (error: any) {
        alert("STOMP error " + error);
      });
    });
  }

  subscribeToTopic(topicName: string) {
    this.connectRef.ws.subscribe(topicName, function (message: any) {
      console.log(message)
    });
  }

  sentData(data: string) {
    this.ws.send("/app/message", {}, data);
  }

  sentMessage(message: MessageDto){
    this.ws.send(this.SEND_MESSAGE_URL, {}, JSON.stringify(message));
  }

  disconnect() {
    if (this.ws != null) {
      this.ws.ws.close();
    }
    console.log("Disconnected");
  }
}
