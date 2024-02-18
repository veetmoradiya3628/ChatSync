import { Component } from '@angular/core';

@Component({
  selector: 'app-global-contacts',
  templateUrl: './global-contacts.component.html',
  styleUrls: ['./global-contacts.component.css']
})
export class GlobalContactsComponent {
  loopCounter = Array(25).fill(0).map((x, i) => i); // Creating an array of length 10

}
