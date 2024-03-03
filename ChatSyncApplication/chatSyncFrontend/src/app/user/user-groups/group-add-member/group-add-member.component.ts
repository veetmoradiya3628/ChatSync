import { ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from 'src/app/models/user.model';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-group-add-member',
  templateUrl: './group-add-member.component.html',
  styleUrls: ['./group-add-member.component.scss']
})
export class GroupAddMemberComponent implements OnInit {
  public groupId: string;
  public groupGlobalMembers: Array<User> = [];

  constructor(public dialogRef: MatDialogRef<GroupAddMemberComponent>,
    private _apiService: ApiService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cdr: ChangeDetectorRef) {
    this.groupId = this.data.groupId;
    console.log(`groupInfo invoked for groupId : ${this.groupId}`)
  }

  ngOnInit(): void {
    this.loadGloabalUsersForGroup();
  }

  public loadGloabalUsersForGroup() {
    this._apiService.getGlobalUsersForGroup(this.groupId).subscribe(
      (res : any) => {
        console.log(res);
        this.groupGlobalMembers = res.data;
      },
      (error : any) => {
        console.log(error)
      }
    )
  }

}
