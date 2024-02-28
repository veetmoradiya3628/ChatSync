import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-group-edit',
  templateUrl: './group-edit.component.html',
  styleUrls: ['./group-edit.component.css']
})
export class GroupEditComponent implements OnInit {
  
  constructor(public dialogRef: MatDialogRef<GroupEditComponent>){

  }
  
  ngOnInit(): void {
    
  }

  closeDialog(){
    this.dialogRef.close();
  }
}
