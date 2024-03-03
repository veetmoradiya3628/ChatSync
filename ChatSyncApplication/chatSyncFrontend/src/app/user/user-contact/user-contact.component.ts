import { Component } from '@angular/core';
import { ContactTabService } from '../service/contact-tab.service';

@Component({
  selector: 'app-user-contact',
  templateUrl: './user-contact.component.html',
  styleUrls: ['./user-contact.component.css']
})
export class UserContactComponent {
  constructor(private contacTabService: ContactTabService) {}

  onTabChanged(event: any): void {
    // Notify the shared service about the tab change
    console.log(`tab change detected with index : ${event.index}`)
    this.contacTabService.tabChanged(event.index);
  }
}
