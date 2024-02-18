import { Component } from '@angular/core';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent {
  loopCounter = Array(20).fill(0).map((x, i) => i); // Creating an array of length 10

}
