import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  ws: any;
  connectRef: any;
  
  constructor() { }

  connect() : Promise<void> {
    return new Promise<void>((resolve) => {
      let socket = new WebSocket("ws://localhost:8080/ws");
      this.ws = Stomp.over(socket);
      this.connectRef = this;
      this.ws.connect({}, (frame : any) => {
        this.connectRef.ws.subscribe("/errors", function(message : any) {
          alert("Error " + message.body);
        });
        
        this.subscribeToTopic("/topic/reply");
        
      }, function(error: any) {
        alert("STOMP error " + error);
      });
    });
  }

  subscribeToTopic(topicName: string){
    this.connectRef.ws.subscribe(topicName, function(message : any) {
      console.log(message)
    });
  }

  sentData(data: string) {
    this.ws.send("/app/message", {}, data);
  }

  disconnect() {
    if (this.ws != null) {
      this.ws.ws.close();
    }
    console.log("Disconnected");
  }
}
