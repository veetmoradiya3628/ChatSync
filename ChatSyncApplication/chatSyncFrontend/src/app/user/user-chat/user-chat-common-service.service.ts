import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { MessageDirection } from 'src/app/models/enums/message_direction.enum';
import { MessageDto } from 'src/app/models/message_dto.model';
import { ThreadDto } from 'src/app/models/thread_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';

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

  constructor(private _apiService: ApiService, public _authService: AuthService) {
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

  addMessageToThread(threadId: string, message: MessageDto) {
    // add new message indicator

    if (this.selectedThreadIdx !== '' && this.selectedThreadIdx !== threadId) {
      // if messageMap not have thread with messages then first add those in message thread map
      if (!this.messageMap.has(threadId)) {
        this.messageMap.set(threadId, [...this.getMessagesForThread(threadId)]);
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
    this.messageMap.get(threadId)?.unshift(message);
    this.updateThreadToPositionZero(threadId, new Date());
  }

  public getMessagesForThread(threadId: string): Array<MessageDto> {
    this._apiService.loadMessagesForThreadAndUser(threadId, this.userId, 0, 100).subscribe(
      (res: any) => {
        console.log(res);
        return res.data.content;
      },
      (error: any) => {
        console.log(error);
      }
    )
    return [];
  }
}
