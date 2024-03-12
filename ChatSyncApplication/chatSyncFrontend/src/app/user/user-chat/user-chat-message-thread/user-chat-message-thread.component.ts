import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserChatCommonServiceService } from '../user-chat-common-service.service';
import { Subscription } from 'rxjs/internal/Subscription';
import { MessageDto } from 'src/app/models/message_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { WebsocketService } from 'src/app/service/websocket.service';
import { ThreadDto } from 'src/app/models/thread_dto.model';
import { WSEvent } from 'src/app/models/ws_event';
import { WSNotificationTypes } from 'src/app/models/enums/ws_notification_types.enum';
import { TextMessageEvent } from 'src/app/models/ws-events/text-event.model';
import { v4 as uuidv4 } from 'uuid';
import { ConversationType } from 'src/app/models/enums/conversation_types.enum';

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
  public newMessage: string = '';

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
    console.log(`sendMessage :: called`)
    console.log(this.selectedThreadInfo);
    if (this.selectedThreadInfo.conversationType == ConversationType.ONE_TO_ONE) {
      console.log(`one to one message thread`);

      let receiverIds = this.selectedThreadInfo.conversationId?.split('_');
      console.log(`receiverIds : ${receiverIds}`);

      let receiverId = '';
      receiverIds?.forEach(id => {
        if(id !== this.userId){
          receiverId = id;
        }
      })
      
      let textMessage: TextMessageEvent = new TextMessageEvent(this.selectedThreadIdx, this.userId, receiverId, this.newMessage, new Date(), true);
      let messageObj: WSEvent = new WSEvent(uuidv4(), WSNotificationTypes.SENT_ONE_TO_ONE_TEXT_MESSAGE, textMessage);
      console.log(messageObj)
      this._wsService.sentMessage(messageObj)

      this.newMessage = '';

      

    } else if (this.selectedThreadInfo.conversationType == ConversationType.GROUP) {
      console.log(`group message yet to implement`);
    }
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
