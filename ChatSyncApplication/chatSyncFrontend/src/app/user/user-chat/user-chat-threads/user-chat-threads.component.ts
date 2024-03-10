import { Component, OnInit } from '@angular/core';
import { ThreadDto } from 'src/app/models/thread_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { CommonConfigService } from 'src/app/service/common-config.service';
import { UserChatCommonServiceService } from '../user-chat-common-service.service';

@Component({
  selector: 'app-user-chat-threads',
  templateUrl: './user-chat-threads.component.html',
  styleUrls: ['./user-chat-threads.component.css']
})
export class UserChatThreadsComponent implements OnInit {
  public userId: string = '';
  public chatThreads: Array<ThreadDto> = [];
  public selectedThreadIdx: string = '';

  constructor(private _apiService: ApiService,
    private _authService: AuthService,
    private _commonConfig: CommonConfigService,
    private _userChatCommonService: UserChatCommonServiceService) {
    this.userId = this._authService.getUserId();
  }

  ngOnInit(): void {
    console.log(`UserChatThreadsComponent :: ngOnInit`);
    this.loadChatThreads();
  }

  public loadChatThreads() {
    this._apiService.getThreadsForUser(this.userId).subscribe(
      (res: any) => {
        console.log(res)
        this.chatThreads = res.data;
        console.log(this.chatThreads)
        this.chatThreads.forEach((thread: any) => {
          if (thread.profileImage === null) {
            thread.profileImage = this._commonConfig.DEFAULT_AVATAR_IMAGE;
          } else {
            thread.profileImage = this._commonConfig.SERVER_URL + thread.profileImage;
          }
        })
        if(this.chatThreads.length > 0){
          this._userChatCommonService.updateSelectedValue(this.chatThreads[0].threadId || '');
        }
      },
      (error: any) => {
        console.log(error);
      }
    )
  }

  onClickThreadTile(threadIdx: string){
    console.log(`onClickThreadTile :: ${threadIdx}`)
    this._userChatCommonService.updateSelectedValue(threadIdx || '');
  }

}
