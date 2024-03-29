import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UserDto } from 'src/app/models/user_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { ContactGroupInfoComponent } from '../contact-group-info/contact-group-info.component';
import { ConfirmationDialogService } from 'src/app/service/confirmation-dialog.service';
import { Subscription } from 'rxjs';
import { ContactTabService } from '../../service/contact-tab.service';
import { GeneralService } from 'src/app/service/general.service';
import { CommonConfigService } from 'src/app/service/common-config.service';
import {UserChatCommonServiceService} from "../../user-chat/user-chat-common-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit, OnDestroy {
  // loopCounter = Array(20).fill(0).map((x, i) => i); // Creating an array of length 10
  public userId: string = '1';
  public contacts: Array<UserDto> = [];
  public selectedContact: UserDto = {};
  public selectedContactIndex = 0;
  private subscription: Subscription;

  constructor(private _authService: AuthService,
    private _apiService: ApiService,
    public dialog: MatDialog,
    private _confirmationDialog: ConfirmationDialogService,
    private _generalService: GeneralService,
    private _contactTabService: ContactTabService,
    private _commonConfig: CommonConfigService,
    private _userChatService: UserChatCommonServiceService,
              private _router: Router) {
    this.userId = this._authService.getUserId();
    this.subscription = this._contactTabService.tabChanged$.subscribe((index) => {
      if (index === 0) {
        // Reload your component method here
        this.loadUserContacts();
      }
    });
  }

  ngOnInit(): void {
    this.loadUserContacts();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  loadUserContacts() {
    this._apiService.getContactsForUser(this.userId).subscribe(
      (res: any) => {
        // Assuming you have a filter condition, you can use the `filter` operator here
        const filteredContacts = res.data
          .filter((element: any) => element)
          .map((element: any) => element.contactDetail);

        filteredContacts.forEach((contact: any) => {
          if (contact.profileImage === null) {
            contact.profileImage = this._commonConfig.DEFAULT_AVATAR_IMAGE;
          } else {
            contact.profileImage = this._commonConfig.SERVER_URL + contact.profileImage;
          }
        });
        console.log(filteredContacts)

        this.contacts = filteredContacts;
        console.log(this.contacts)
        if (this.contacts.length > 0) {
          this.selectedContact = this.contacts[this.selectedContactIndex];
        }
      },
      (error: any) => {
        console.log(error);
      }
    )
  }

  changeSelectedContact(userIdx: any) {
    this.selectedContactIndex = userIdx;
    this.selectedContact = this.contacts[this.selectedContactIndex];
  }

  onClickViewGroupsForUser() {
    const dialogRef = this.dialog.open(ContactGroupInfoComponent, {
      width: '1200px',
      height: '700px', // Set the width as needed
      data: { userId: this.selectedContact.userId }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  onClickChatWithContact(userId: any) {
    this._confirmationDialog.openConfirmationDialog('Are you sure want to start chat ?').then((result) => {
      if (result) {
        console.log(`clicked yes for userId : ${userId}`)

        let reqObj: any = {};
        reqObj['userA'] = userId;
        reqObj['userB'] = this.userId;
        reqObj['conversationType'] = "ONE_TO_ONE";

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

  onClickDeleteContact(contactId: any) {
    this._confirmationDialog.openConfirmationDialog('Are you sure want to delete this contact?').then((result) => {
      if (result) {
        console.log(`clicked yes for contactId : ${contactId} for delete ops`)

        let reqObj = {
          userId: this.userId,
          contactId
        }
        console.log(reqObj);

        this._apiService.deleteUserFromContact(reqObj).subscribe(
          (res: any) => {
            console.log(res);
            this.loadUserContacts();
            this._generalService.openSnackBar('Contact deleted successfully!!', 'Ok')
          },
          (error: any) => {
            console.log(error);
            this._generalService.openSnackBar('Error while deleting contact!!', 'Ok')
          }
        )
      } else {
        console.log(`clicked no`);
        return;
      }
    })
  }
}
