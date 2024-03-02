import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserGroupDto } from 'src/app/models/user_group_dto.model';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-contact-group-info',
  templateUrl: './contact-group-info.component.html',
  styleUrls: ['./contact-group-info.component.scss']
})
export class ContactGroupInfoComponent implements OnInit {

  userId!: string;
  public groups: Array<UserGroupDto> = [];

  constructor(public dialogRef: MatDialogRef<ContactGroupInfoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public _apiService: ApiService) {
    this.userId = data.userId;
    console.log(`selected userId : ${this.userId}`);
  }

  ngOnInit(): void {
    this.loadGroupsForUser();
  }

  public loadGroupsForUser() {
    this._apiService.getGroupsForUserAPI(this.userId).subscribe(
      (res: any) => {
        this.groups = res.data;
        console.log(this.groups);
      },
      (error: any) => {
        console.log(error);
      }
    )
  }

}
