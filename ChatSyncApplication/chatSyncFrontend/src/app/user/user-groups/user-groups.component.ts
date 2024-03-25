import { Component } from '@angular/core';
import {ContactTabService} from "../service/contact-tab.service";
import {GroupTabService} from "../service/group-tab.service";

@Component({
  selector: 'app-user-groups',
  templateUrl: './user-groups.component.html',
  styleUrls: ['./user-groups.component.css']
})
export class UserGroupsComponent {
  constructor(private groupTabService: GroupTabService) {}

  onTabChanged(event: any): void {
    // Notify the shared service about the tab change
    console.log(`tab change detected with index : ${event.index}`)
    this.groupTabService.tabChanged(event.index);
  }
}
