import { Component } from '@angular/core';

@Component({
  selector: 'app-create-new-group',
  templateUrl: './create-new-group.component.html',
  styleUrls: ['./create-new-group.component.css']
})
export class CreateNewGroupComponent {
  loopCounter = Array(30).fill(0).map((x, i) => i); // Creating an array of length 10

}
