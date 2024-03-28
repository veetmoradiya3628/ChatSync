import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs/internal/BehaviorSubject';
import {MessageDirection} from 'src/app/models/enums/message_direction.enum';
import {MessageDto} from 'src/app/models/message_dto.model';
import {ThreadDto} from 'src/app/models/thread_dto.model';
import {ApiService} from 'src/app/service/api.service';
import {AuthService} from 'src/app/service/auth.service';
import {GeneralService} from "../../service/general.service";
import {MessageTypes} from "../../models/enums/message_types.enum";
import {MessageStatus} from "../../models/enums/message_status.enum";

@Injectable({
  providedIn: 'root'
})
export class UserChatCommonServiceService {
  private messageMap: Map<string, Array<MessageDto>>;
  public threads: Array<ThreadDto>;
  public userId!: string;

  public selectedThreadIdx = '';

  private selectedThreadValueSubject = new BehaviorSubject<string>('0');
  public selectedThreadValueSubject$ = this.selectedThreadValueSubject.asObservable();

  private threadsSubject = new BehaviorSubject<Array<ThreadDto>>([]);
  threads$ = this.threadsSubject.asObservable();

  constructor(private _apiService: ApiService, public _authService: AuthService, public _generalService: GeneralService) {
    this.threads = [];
    this.messageMap = new Map<string, Array<MessageDto>>;
    this.userId = this._authService.getUserId();
  }

  // utility methods for threads

  addNewThread(thread: ThreadDto) {
    this.threads.push(thread);
  }

  removeThread(threadId: string): void {
    this.threads = this.threads.filter((thread) => thread.threadId !== threadId);
  }

  updateThread(updatedThread: ThreadDto): void {
    const index = this.threads.findIndex((thread) => thread.threadId === updatedThread.threadId);
    if (index !== -1) {
      this.threads[index] = updatedThread;
    }
    this.threadsSubject.next(this.threads);
  }

  getThreadReadStatus(threadId: string) {
    return this.findThreadById(threadId)?.isReadPending || false;
  }


  updateThreadToPositionZero(selectedThreadIdx: string, lastMessageSentAt: Date) {
    const idx = this.threads.findIndex((thread) => thread.threadId === selectedThreadIdx);
    if (idx !== -1) {
      const threadToMove = this.threads.splice(idx, 1)[0];
      threadToMove.updatedAt = lastMessageSentAt;
      this.threads.unshift(threadToMove);

      this.threadsSubject.next(this.threads);
    }
  }

  findThreadById(threadId: string): ThreadDto | undefined {
    return this.threads.find((thread) => thread.threadId === threadId);
  }

  getAllThreads(): Array<ThreadDto> {
    console.log(`called :: getAllThreads`);
    return this.threads;
  }

  // utility methods for thread and message mapping

  updateSelectedValue(threadId: string): void {
    this.selectedThreadIdx = threadId;
    this.selectedThreadValueSubject.next(threadId);
  }

  addThreadToMap(threadId: string, messages: Array<MessageDto>) {
    if (!this.messageMap.has(threadId)) {
      this.messageMap.set(threadId, messages);
    }
  }

  getMessagesOfThread(threadId: string): Array<MessageDto> {
    return this.messageMap.get(threadId) || [];
  }

  deleteThread(threadId: string) {
    this.messageMap.delete(threadId);
  }

  clearMessagesOfThread(threadId: string) {
    this.messageMap.set(threadId, []);
  }

  checkMapContainsThread(threadId: string): boolean {
    return this.messageMap.has(threadId);
  }

  async addMessageToThread(threadId: string, message: MessageDto) {
    // add new message indicator
    if (this.selectedThreadIdx !== '' && this.selectedThreadIdx !== threadId) {
      // if messageMap not have thread with messages then first add those in message thread map
      if (!this.messageMap.has(threadId)) {
        const messages = await this.getMessagesForThread(threadId);
        messages.shift();
        this.messageMap.set(threadId, messages);
      }

      let thread: any = this.findThreadById(threadId) || {};
      if (!thread.isReadPedning) {
        thread.isReadPedning = true;
        const newMessage: MessageDto = {
          messageDirection: MessageDirection.NEW_MESSAGE_START,
          senderId: 'null'
        };
        this.messageMap.get(threadId)?.unshift(newMessage);
      }

      thread.pendingToReadMessageCnt = thread.pendingToReadMessageCnt + 1;
    }
    message.messageStatus = MessageStatus.SENT;

    this.messageMap.get(threadId)?.unshift(message);
    this.updateThreadToPositionZero(threadId, new Date());

    // message receive notification
    if (this.selectedThreadIdx !== threadId) {
      switch (message.messageType) {
        case MessageTypes.ONE_TO_ONE_TEXT:
          this._generalService.openSnackBar("One to One message received!!", 'Ok')
          break
        case MessageTypes.GROUP_TEXT:
          this._generalService.openSnackBar("Group message received!!", 'Ok')
          break
      }
    }
  }

  public async getMessagesForThread(threadId: string): Promise<Array<MessageDto>> {
    try {
      const res: any = await this._apiService.loadMessagesForThreadAndUser(threadId, this.userId, 0, 12).toPromise();
      console.log(res);
      return res.data.content;
    } catch (error) {
      console.log(error);
      return [];
    }
  }

  public removeNewMessageIndicator(selectedThreadIdx: string) {
    console.log(`going for clean up of message from thread: ${selectedThreadIdx}`)
    let messages = this.messageMap.get(selectedThreadIdx);
    let filteredMessages = messages?.filter(message => message.messageDirection !== MessageDirection.NEW_MESSAGE_START) || []
    this.messageMap.set(selectedThreadIdx, [...filteredMessages]);
    return filteredMessages;
  }

  public markThreadInfoAsRead(threadId: string) {
    let thread: any = this.findThreadById(threadId);
    if (thread !== undefined && thread.pendingToReadMessageCnt > 0) {
      thread.pendingToReadMessageCnt = 0;
      thread.isReadPedning = false;
    }
    this.updateThread(thread);
  }

  public async loadFirstPageMessageForThreads() {
    console.log(`loadFirstPageMessageForThreads started...`)
    for (const thread of this.threads) {
      if (thread.threadId) {
        const messages = await this.getMessagesForThread(thread.threadId);
        this.messageMap.set(thread.threadId, messages);
      }
    }
    console.log(`loadFirstPageMessageForThreads finished...`)
  }

  async getMessagesOfThreadPage(selectedThreadIdx: string, pageNumber: number) {
    if (!this.findThreadById(selectedThreadIdx)?.isLastPage) {
      const res: any = await this._apiService.loadMessagesForThreadAndUser(selectedThreadIdx, this.userId, pageNumber, 12).toPromise();
      console.log(res);
      let messages = res.data.content;
      let isLastPage = res.data.last;
      let threadInfo: any = this.findThreadById(selectedThreadIdx) || {};
      threadInfo.isLastPage = isLastPage;
      threadInfo.lastLoadedPageNumber = pageNumber + 1;
      this.updateThread(threadInfo);

      let threadMessage = this.messageMap.get(selectedThreadIdx);
      this.messageMap.set(selectedThreadIdx, (threadMessage || []).concat(messages));

      return messages;
    }
  }

}
