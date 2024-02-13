import { Component } from '@angular/core';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent {
  contacts = [
    { name: 'John Doe', status: 'Online' },
    { name: 'Jane Smith', status: 'Away' },
    // Add more contacts as needed
  ];
}
