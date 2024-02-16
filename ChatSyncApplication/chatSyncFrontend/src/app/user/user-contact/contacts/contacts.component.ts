import { Component } from '@angular/core';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent {
  loopCounter = Array(20).fill(0).map((x, i) => i); // Creating an array of length 10
}
