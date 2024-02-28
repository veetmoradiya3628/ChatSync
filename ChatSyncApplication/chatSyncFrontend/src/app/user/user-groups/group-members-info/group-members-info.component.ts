import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from 'src/app/models/user.model';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-group-members-info',
  templateUrl: './group-members-info.component.html',
  styleUrls: ['./group-members-info.component.css']
})
export class GroupMembersInfoComponent implements OnInit {
  public groupInfo: any;
  public groupId: string;
  public groupMembers: Array<User> = [];

  constructor(public dialogRef: MatDialogRef<GroupMembersInfoComponent>,
    private _apiService: ApiService,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.groupId = this.data.groupId;
    console.log(`groupInfo invoked for groupId : ${this.groupId}`)
  }

  ngOnInit(): void {
    this.loadGroupInformation();
  }

  loadGroupInformation() {
    this._apiService.getGroupInformationByGroupId(this.groupId).subscribe(
      (res: any) => {
        console.log(res)
        this.groupInfo = res.data;
        res.data.admins.forEach((user: any) => {
          this.groupMembers.push(user);
        });

        res.data.members.forEach((user: any) => {
          this.groupMembers.push(user);
        })

        console.log(this.groupMembers);
      },
      (error: any) => {
        console.log(error)
      }
    )

  }

  closeDialog(): void {
    this.dialogRef.close();
  }

}
