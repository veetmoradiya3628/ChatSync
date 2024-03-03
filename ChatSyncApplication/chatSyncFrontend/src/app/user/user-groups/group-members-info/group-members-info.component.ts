import { ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { switchMap, of, catchError } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-group-members-info',
  templateUrl: './group-members-info.component.html',
  styleUrls: ['./group-members-info.component.scss']
})
export class GroupMembersInfoComponent implements OnInit {
  public groupInfo: any;
  public groupId: string;
  public groupMembers: Array<User> = [];

  constructor(public dialogRef: MatDialogRef<GroupMembersInfoComponent>,
    private _apiService: ApiService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cdr: ChangeDetectorRef) {
    this.groupId = this.data.groupId;
    console.log(`groupInfo invoked for groupId : ${this.groupId}`)
  }

  ngOnInit(): void {
    this.loadGroupInformation();
  }

  loadGroupInformation() {
    this._apiService.getGroupInformationByGroupId(this.groupId)
    .pipe(
      switchMap((res: any) => {
        this.groupInfo = res.data;
        const admins = res.data.admins.map((user: any) => ({ ...user, role: 'admin' }));
        const members = res.data.members.map((user: any) => ({ ...user, role: 'member' }));
        this.groupMembers = [...admins, ...members];
        console.log(this.groupMembers);
        return of(this.groupMembers);
      }),
      catchError((error: any) => {
        console.log(error);
        return of([]);
      })
    )
    .subscribe(() => {
      this.cdr.detectChanges();
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

}
