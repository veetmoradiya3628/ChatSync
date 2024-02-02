import { Component } from '@angular/core';
import * as Stomp from 'stompjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  greetings: string[] = [];
  showConversation: boolean = false;
  ws: any;
  name: string = '';
  disabled: boolean = false;

  constructor(){}

  connect() {
    let socket = new WebSocket("ws://localhost:8080/ws");
    this.ws = Stomp.over(socket);
    let that = this;
    this.ws.connect({}, function(frame : any) {
      that.ws.subscribe("/errors", function(message : any) {
        alert("Error " + message.body);
      });
      that.ws.subscribe("/topic/reply", function(message : any) {
        console.log(message)
        that.showGreeting(message.body);
      });
      that.disabled = true;
    }, function(error: any) {
      alert("STOMP error " + error);
    });
  }

  disconnect() {
    if (this.ws != null) {
      this.ws.ws.close();
    }
    this.setConnected(false);
    console.log("Disconnected");
  }

  sendName() {
    let data = this.name;
    this.ws.send("/app/message", {}, data);
  }

  showGreeting(message: any) {
    this.showConversation = true;
    this.greetings.push(message)
    console.log(this.greetings);
  }

  setConnected(connected: any) {
    this.disabled = connected;
    this.showConversation = connected;
    this.greetings = [];
  }
}
