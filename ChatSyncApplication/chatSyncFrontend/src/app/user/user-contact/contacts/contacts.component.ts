import { Component, OnInit } from '@angular/core';
import { UserDto } from 'src/app/models/user_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {
  // loopCounter = Array(20).fill(0).map((x, i) => i); // Creating an array of length 10 
  public userId: string = '1';
  public contacts: Array<UserDto> = [];
  public selectedContact: UserDto = {};
  public selectedContactIndex = 0;

  constructor(private _authService: AuthService,
    private _apiService: ApiService) {
    this.userId = this._authService.getUserId();
  }
  ngOnInit(): void {
    this.loadUserContacts();
  }

  loadUserContacts() {
    this._apiService.getContactsForUser(this.userId).subscribe(
      (res: any) => {
        // Assuming you have a filter condition, you can use the `filter` operator here
        const filteredContacts = res.data
          .filter((element: any) => element)
          .map((element: any) => element.contactDetail);
  
        this.contacts = filteredContacts;
  
        if (this.contacts.length > 0) {
          this.selectedContact = this.contacts[this.selectedContactIndex];
        }
      },
      (error: any) => {
        console.log(error);
      }
    )
  }

  changeSelecetdContact(userIdx: any) {
    this.selectedContactIndex = userIdx;
    this.selectedContact = this.contacts[this.selectedContactIndex];
  }
}
