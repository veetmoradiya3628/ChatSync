import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserChatCommonServiceService } from '../user-chat-common-service.service';
import { Subscription } from 'rxjs/internal/Subscription';
import { MessageDto } from 'src/app/models/message_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { WebsocketService } from 'src/app/service/websocket.service';
import { ThreadDto } from 'src/app/models/thread_dto.model';


@Component({
  selector: 'app-user-chat-message-thread',
  templateUrl: './user-chat-message-thread.component.html',
  styleUrls: ['./user-chat-message-thread.component.css']
})
export class UserChatMessageThreadComponent implements OnInit, OnDestroy {
  isCardVisible = false;
  public selectedThreadIdx: string = '0';
  public userId: string = '';
  private subscription: Subscription;
  public threadMessages: Array<MessageDto> = [];
  public selectedThreadInfo!: ThreadDto;
  newMessage: string = '';

  constructor(private _userChatCommonService: UserChatCommonServiceService,
    private _apiService: ApiService,
    private _authService: AuthService,
    private _wsService: WebsocketService) {
    this.subscription = this._userChatCommonService.selectedThreadValueSubject$.subscribe(
      (value: any) => {
        this.selectedThreadIdx = value;
        this.selectedThreadInfo = this._userChatCommonService.findThreadById(this.selectedThreadIdx) || {};
        console.log(`UserChatMessageThreadComponent :: threadId : ${this.selectedThreadIdx}`)
        this.loadMessagesForThread();
      }
    );
    this.userId = this._authService.getUserId();
  }

  ngOnInit(): void {
  }


  loadMessagesForThread() {
    if (this.selectedThreadIdx !== '0') {
      if (this._userChatCommonService.checkMapContainsThread(this.selectedThreadIdx)) {
        this.threadMessages = this._userChatCommonService.getMessagesOfThread(this.selectedThreadIdx);
      } else {
        this._apiService.loadMessagesForThreadAndUser(this.selectedThreadIdx, this.userId, 0, 100).subscribe(
          (res: any) => {
            console.log(res);
            this.threadMessages = res.data.content;
            console.log(this.threadMessages);
            this._userChatCommonService.addThreadToMap(this.selectedThreadIdx, this.threadMessages);
            // this.threadMessages = this.threadMessages.reverse(); 
          },
          (error: any) => {
            console.log(error);
          }
        )
      }
    }
  }


  sendMessage() {
    console.log(`sendMessage called`)
    let message: MessageDto = {
      senderId: "user123",
      messageContent: "123454",
      receiverId: "1234",
      threadId: "thread789",
    }
    // Logic to send message to other user or backend can be added here
    this._wsService.sentMessage(message)
  }

  showUserInfoCard() {
    this.isCardVisible = true;
  }

  hideUserInfoCard() {
    this.isCardVisible = false;
  }

  ngOnDestroy(): void {
    // Unsubscribe to avoid memory leaks
    this.subscription.unsubscribe();
  }

}
