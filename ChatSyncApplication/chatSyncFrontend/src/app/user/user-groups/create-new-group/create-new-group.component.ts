import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { CommonConfigService } from 'src/app/service/common-config.service';
import { ConfirmationDialogService } from 'src/app/service/confirmation-dialog.service';
import { GeneralService } from 'src/app/service/general.service';
import {Subscription} from "rxjs";
import {GroupTabService} from "../../service/group-tab.service";

@Component({
  selector: 'app-create-new-group',
  templateUrl: './create-new-group.component.html',
  styleUrls: ['./create-new-group.component.css']
})
export class CreateNewGroupComponent implements OnInit {
  public groupName: string = '';
  public userId: string;
  public contacts: Array<User> = [];
  public addedMembers: Array<User> = [];
  private subscription: Subscription;


  constructor(private _apiService: ApiService,
    private _authService: AuthService,
    private _cdr: ChangeDetectorRef,
    private _generalService: GeneralService,
    private _confirmationDialog: ConfirmationDialogService,
    private _commonConfig: CommonConfigService,
              private _groupTabService: GroupTabService) {
    this.userId = this._authService.getUserId();
    this.subscription = this._groupTabService.tabChanged$.subscribe((index) => {
      console.log(index)
      if (index === 1) {
        // Reload your component method here
        this.loadContactsForUser();
      }
    });
  }

  ngOnInit(): void {
    this.loadContactsForUser();
  }

  loadContactsForUser() {
    this._apiService.getContactsForUser(this.userId).subscribe(
      (res: any) => {
        this.contacts = res.data;
        console.log(this.contacts);
        this.contacts.forEach((contact: any) => {
          let contact_detail = contact.contactDetail;
          if (contact_detail.profileImage === null) {
            contact_detail.profileImage = this._commonConfig.DEFAULT_AVATAR_IMAGE;
          } else {
            contact_detail.profileImage = this._commonConfig.SERVER_URL + contact_detail.profileImage;
          }
        })
        this.addedMembers = [];
      },
      (error: any) => {
        console.log(error)
      }
    )
  }

  onClickAddToGroup(index: number) {
    console.log(index);
    const newMember = this.contacts[index];
    console.log(newMember);
    this.contacts.splice(index, 1);
    this.contacts = [...this.contacts];
    this.addedMembers = [...this.addedMembers, newMember];
    this._cdr.detectChanges();
  }

  onClickRemoveFromAddedMembers(index: number) {
    console.log(`onClickRemoveFromAddedMembers called with index : ${index}`)
    const memberInfo = this.addedMembers[index];
    this.addedMembers.splice(index, 1);
    this.addedMembers = [...this.addedMembers];
    this.contacts = [...this.contacts, memberInfo];
    this._cdr.detectChanges();
  }

  onClickReset() {
    this.loadContactsForUser();
    this.groupName = '';
  }

  onClickCreateGroup() {
    console.log(`onClickCreateGroup called`)
    if (this.groupName !== '') {
      this._confirmationDialog.openConfirmationDialog('Are you sure want to create this group ?').then((result) => {
        if (result) {
          let req: any = {};
          req['groupName'] = this.groupName;

          // adding admin
          req['admins'] = [];
          req['members'] = [];

          let admin = {
            userId: this.userId
          }
          req['admins'].push(admin)

          this.addedMembers.forEach((member: any) => {
            console.log(member.contactDetail.userId);
            let memberObj = {
              userId: member.contactDetail.userId
            }
            req['members'].push(memberObj);
          })
          console.log(`reqObject for create group`)
          console.log(req);

          this._apiService.createGroupAPI(req).subscribe(
            (res: any) => {
              console.log(res);
              this._generalService.openSnackBar('Group created successfully!!', 'Ok');
              this.loadContactsForUser();
            },
            (error: any) => {
              console.log(error);
              this._generalService.openSnackBar('Error while creating group!!', 'Ok');
            }
          )
        } else {
          return;
        }
      })
    } else {
      this._generalService.openSnackBar('Group name can not be empty!', 'ok');
      return;
    }
  }
}
