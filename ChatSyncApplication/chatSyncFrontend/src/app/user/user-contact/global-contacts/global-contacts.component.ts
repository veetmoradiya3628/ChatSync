import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UserDto } from 'src/app/models/user_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { ContactGroupInfoComponent } from '../contact-group-info/contact-group-info.component';
import { ConfirmationDialogService } from 'src/app/service/confirmation-dialog.service';

@Component({
  selector: 'app-global-contacts',
  templateUrl: './global-contacts.component.html',
  styleUrls: ['./global-contacts.component.css']
})
export class GlobalContactsComponent implements OnInit {

  // loopCounter = Array(25).fill(0).map((x, i) => i); // Creating an array of length 10
  public userId: string = '1';
  public globalUsers: Array<UserDto> = [];
  public selectedUser: UserDto = {};
  public selectedUserIndex = 0;

  constructor(private _authService: AuthService,
    private _apiService: ApiService,
    public dialog: MatDialog,
    private _confirmationDialog: ConfirmationDialogService) {
    this.userId = this._authService.getUserId();
  }

  ngOnInit(): void {
    this.loadgloabalContacts();
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
        
      } else {
        return;
      }
    })
  }
}
