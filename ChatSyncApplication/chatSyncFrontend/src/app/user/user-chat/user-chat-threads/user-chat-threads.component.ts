import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { ThreadDto } from 'src/app/models/thread_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { CommonConfigService } from 'src/app/service/common-config.service';
import { UserChatCommonServiceService } from '../user-chat-common-service.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user-chat-threads',
  templateUrl: './user-chat-threads.component.html',
  styleUrls: ['./user-chat-threads.component.css']
})
export class UserChatThreadsComponent implements OnInit, OnDestroy {
  public userId: string = '';
  public chatThreads: Array<ThreadDto> = [];
  public selectedThreadIdx: string = '';

  constructor(private _apiService: ApiService,
    private _authService: AuthService,
    private _commonConfig: CommonConfigService,
    private _userChatCommonService: UserChatCommonServiceService,
    private cdr: ChangeDetectorRef) {
    this.userId = this._authService.getUserId();
    console.log(`UserChatThreadsComponent :: ngOnInit`);

    this._userChatCommonService.threads$.subscribe(threads => {
      this.loadChatThreads();
    })
  }

  ngOnInit(): void {
    this.loadChatThreads();
  }

  public loadChatThreads() {
    if (this._userChatCommonService.threads.length == 0) {
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
            thread.pendingToReadMessageCnt = 0; // initially pending to read message cnt would be 0
          })
          if (this.chatThreads.length > 0) {
            this._userChatCommonService.updateSelectedValue(this.chatThreads[0].threadId || '');
            this._userChatCommonService.threads = this.chatThreads;

            this._userChatCommonService.loadFirstPageMessageForThreads();
          }
        },
        (error: any) => {
          console.log(error);
        }
      )
    } else {
      this.chatThreads = [...this._userChatCommonService.getAllThreads()];
    }
  }

  onClickThreadTile(threadIdx: string) {
    console.log(`onClickThreadTile :: ${threadIdx}`)
    this._userChatCommonService.updateSelectedValue(threadIdx || '');
  }

  ngOnDestroy(): void {
    // Unsubscribe to avoid memory leaks
  }
}
