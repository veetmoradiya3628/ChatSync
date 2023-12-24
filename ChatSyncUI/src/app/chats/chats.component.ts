import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-chats',
  templateUrl: './chats.component.html',
  styleUrls: ['./chats.component.css']
})
export class ChatsComponent implements OnInit {
  loopCounter = Array(10).fill(0).map((x, i) => i); // Creating an array of length 10
  constructor() { }

  ngOnInit(): void {
  }

}
