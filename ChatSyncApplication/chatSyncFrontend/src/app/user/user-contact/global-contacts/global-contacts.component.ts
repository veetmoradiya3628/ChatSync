import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UserDto } from 'src/app/models/user_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { ContactGroupInfoComponent } from '../contact-group-info/contact-group-info.component';
import { ConfirmationDialogService } from 'src/app/service/confirmation-dialog.service';
import { GeneralService } from 'src/app/service/general.service';
import { Subscription } from 'rxjs';
import { ContactTabService } from '../../service/contact-tab.service';

@Component({
  selector: 'app-global-contacts',
  templateUrl: './global-contacts.component.html',
  styleUrls: ['./global-contacts.component.css']
})
export class GlobalContactsComponent implements OnInit, OnDestroy {

  // loopCounter = Array(25).fill(0).map((x, i) => i); // Creating an array of length 10
  public userId: string = '1';
  public globalUsers: Array<UserDto> = [];
  public selectedUser: UserDto = {};
  public selectedUserIndex = 0;
  private subscription: Subscription;

  constructor(private _authService: AuthService,
    private _apiService: ApiService,
    public dialog: MatDialog,
    private _confirmationDialog: ConfirmationDialogService,
    private _generalService: GeneralService,
    private _contactTabService: ContactTabService) {
    this.userId = this._authService.getUserId();
    this.subscription = this._contactTabService.tabChanged$.subscribe((index) => {
      if (index === 1) {
        // Reload your component method here
        this.loadgloabalContacts();
      }
    });
  }

  ngOnInit(): void {
    console.log(`ngOnInit : GlobalContactsComponent`)
    this.loadgloabalContacts();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  loadgloabalContacts() {
    this._apiService.getGlobalContactsForUser(this.userId).subscribe(
      (res: any) => {
        this.globalUsers = res.data;
        console.log(this.globalUsers);
        if (this.globalUsers) {
          this.selectedUser = this.globalUsers[this.selectedUserIndex];
          console.log(this.selectedUser);
        }
      },
      (error: any) => {
        console.log(error)
      }
    )
  }

  changeSelecetdUser(userIdx: any) {
    this.selectedUserIndex = userIdx;
    this.selectedUser = this.globalUsers[this.selectedUserIndex];
  }

  onClickViewGroupsForUser() {
    const dialogRef = this.dialog.open(ContactGroupInfoComponent, {
      width: '1200px',
      height: '700px', // Set the width as needed
      data: { userId: this.selectedUser.userId }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  onClickAddToContact() {
    this._confirmationDialog.openConfirmationDialog('Are you sure want to add this user as contact ?').then((result) => {
      if (result) {
        let userId = this._authService.getUserId();
        let contactId = this.selectedUser.userId;
        let reqObj = {
          userId,
          contactId
        }
        console.log(reqObj)
        this._apiService.addUserToContactAPI(reqObj).subscribe(
          (res: any) => {
            console.log(res);
            this._generalService.openSnackBar('User added as contact!!', 'Ok')
            this.loadgloabalContacts();
          },
          (error: any) => {
            console.log(error)
            this._generalService.openSnackBar('Error while adding as contact!!', 'Ok')
          }
        )
      } else {
        return;
      }
    })
  }
}
