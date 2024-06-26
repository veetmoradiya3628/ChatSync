import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UserGroupDto } from 'src/app/models/user_group_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { GroupMembersInfoComponent } from '../group-members-info/group-members-info.component';
import { GroupEditComponent } from '../group-edit/group-edit.component';
import { GroupAddMemberComponent } from '../group-add-member/group-add-member.component';
import { ConfirmationDialogService } from 'src/app/service/confirmation-dialog.service';
import { CommonConfigService } from 'src/app/service/common-config.service';
import {Subscription} from "rxjs";
import {GroupTabService} from "../../service/group-tab.service";
import {GeneralService} from "../../../service/general.service";
import {UserChatCommonServiceService} from "../../user-chat/user-chat-common-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {

  // loopCounter = Array(20).fill(0).map((x, i) => i); // Creating an array of length 10
  public userId!: string;
  public groups: Array<UserGroupDto> = [];
  public selectedGroup: UserGroupDto | undefined;
  public selectedGroupIndex = 0;
  private subscription: Subscription;


  constructor(private _authService: AuthService,
    private _apiService: ApiService,
    public dialog: MatDialog,
    private _confirmationDialog: ConfirmationDialogService,
    private _commonConfig: CommonConfigService,
              private _groupTabService: GroupTabService,
               private _generalService: GeneralService,
               private _userChatService: UserChatCommonServiceService,
              private _router: Router) {
    this.userId = this._authService.getUserId();
    this.subscription = this._groupTabService.tabChanged$.subscribe((index) => {
      console.log(index)
      if (index === 0) {
        // Reload your component method here
        this.loadGroupsForUser();
      }
    });
  }

  ngOnInit(): void {
    this.loadGroupsForUser();
  }

  loadGroupsForUser() {
    this._apiService.getGroupsForUserAPI(this.userId).subscribe(
      (res: any) => {
        console.log(res)
        this.groups = res.data;
        this.groups.forEach((group: any) => {
          if(group.groupProfile === null){
            group.groupProfile = this._commonConfig.DEFAULT_AVATAR_IMAGE;
          }else{
            group.groupProfile = this._commonConfig.SERVER_URL + group.groupProfile;
          }
        })
        // selected group info handling
        if (this.groups) {
          this.selectedGroup = this.groups[this.selectedGroupIndex];
        }
      },
      (error: any) => {
        console.log(error)
      }
    )
  }

  changeSelectedGroup(groupIdx: any) {
    this.selectedGroupIndex = groupIdx;
    this.selectedGroup = this.groups[this.selectedGroupIndex];
  }

  showMembersInfoForGroup() {
    // let groupId: string = this.selectedGroup?.groupId;
    const dialogRef = this.dialog.open(GroupMembersInfoComponent, {
      width: '1200px',
      height: '700px', // Set the width as needed
      data: { groupId: this.selectedGroup?.groupId }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  editGroup(){
    // let groupId: string = this.selectedGroup?.groupId;
    const dialogRef = this.dialog.open(GroupEditComponent, {
      width: '1200px',
      height: '700px', // Set the width as needed
      data: { groupId: this.selectedGroup?.groupId }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  addNewMember(){
    // let groupId: string = this.selectedGroup?.groupId;
    const dialogRef = this.dialog.open(GroupAddMemberComponent, {
      width: '1200px',
      height: '700px', // Set the width as needed
      data: { groupId: this.selectedGroup?.groupId }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  onClickChatWithGroup(groupId: any){
    this._confirmationDialog.openConfirmationDialog('Are you sure want to start chat ?').then((result) => {
      if (result) {
        console.log(`clicked yes for groupId : ${groupId}`)

        let reqObj: any = {};
        reqObj['conversationGroupId'] = groupId;
        reqObj['conversationType'] = "GROUP";

        console.log(reqObj)

        this._apiService.createChatThreadAPI(reqObj).subscribe(
          (res: any) => {
            console.log(res)
            this._generalService.openSnackBar("chat started successfully!!", "Ok");
            this._userChatService.startNewConversationOnThread(res.data);

            setTimeout(() => {
              this._router.navigate(["/user/chat"])
            }, 1000)

          },
          (error : any) => {
            console.log(error)
          }
        )

      } else {
        console.log(`clicked no`);
        return;
      }
    })
  }

  onClickLeaveGroup(groupId: any){
    this._confirmationDialog.openConfirmationDialog('Are you sure want to leave this group ?').then((result) => {
      if (result) {
        console.log(`clicked yes for groupId : ${groupId}`)
      } else {
        console.log(`clicked no`);
        return;
      }
    })
  }

  onClickDeleteGroup(groupId: any){
    this._confirmationDialog.openConfirmationDialog('Are you sure want to delete this group ?').then((result) => {
      if (result) {
        console.log(`clicked yes for groupId : ${groupId}`)
      } else {
        console.log(`clicked no`);
        return;
      }
    })
  }

}
