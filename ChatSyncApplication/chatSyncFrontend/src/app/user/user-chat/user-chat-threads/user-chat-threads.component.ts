import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-chat-threads',
  templateUrl: './user-chat-threads.component.html',
  styleUrls: ['./user-chat-threads.component.css']
})
export class UserChatThreadsComponent implements OnInit{ 
  loopCounter = Array(20).fill(0).map((x, i) => i); // Creating an array of length 10
  constructor() { }

  ngOnInit(): void {
  }

}
