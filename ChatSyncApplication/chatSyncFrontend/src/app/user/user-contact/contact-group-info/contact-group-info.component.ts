import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-contact-group-info',
  templateUrl: './contact-group-info.component.html',
  styleUrls: ['./contact-group-info.component.css']
})
export class ContactGroupInfoComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ContactGroupInfoComponent>){}

  ngOnInit(): void {
  }

}
