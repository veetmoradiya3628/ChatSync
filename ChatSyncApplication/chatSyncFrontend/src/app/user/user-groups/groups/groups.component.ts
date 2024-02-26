import { Component, OnInit } from '@angular/core';
import { UserGroupDto } from 'src/app/models/user_group_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {
  loopCounter = Array(20).fill(0).map((x, i) => i); // Creating an array of length 10
  public userId!: string;
  public groups: Array<UserGroupDto> = [];
  public selectedGroup: any = {};
  public selectedGroupIndex = 0;

  constructor(private _authService: AuthService,
    private _apiService: ApiService) {
    this.userId = this._authService.getUserId();
  }

  ngOnInit(): void {
    this.loadGroupsForUser();
  }

  loadGroupsForUser(){
    this._apiService.getGroupsForUserAPI(this.userId).subscribe(
      (res: any) => {
        console.log(res)
        this.groups = res.data;
        

        // selected group info handling
      },
      (error : any) => {
        console.log(error)
      }
    )
  }

}
