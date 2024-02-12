import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebsocketService } from 'src/app/service/websocket.service';

@Component({
  selector: 'app-user-chat',
  templateUrl: './user-chat.component.html',
  styleUrls: ['./user-chat.component.css']
})
export class UserChatComponent implements OnInit, OnDestroy {
 
  constructor(private _wsService: WebsocketService){}
  

  ngOnInit(): void {
    this._wsService.connect();

    setTimeout(() => {
      this._wsService.sentData("random text message : " + Date.now().toString())
    }, 1000)
  }

  ngOnDestroy(): void {
    this._wsService.disconnect();
  }

}
