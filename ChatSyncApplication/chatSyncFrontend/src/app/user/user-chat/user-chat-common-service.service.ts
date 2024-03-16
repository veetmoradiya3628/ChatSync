import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { MessageDirection } from 'src/app/models/enums/message_direction.enum';
import { MessageDto } from 'src/app/models/message_dto.model';
import { ThreadDto } from 'src/app/models/thread_dto.model';

@Injectable({
  providedIn: 'root'
})
export class UserChatCommonServiceService {
  private messageMap: Map<string, Array<MessageDto>>;
  public threads: Array<ThreadDto>;
  
  private selectedThreadValueSubject = new BehaviorSubject<string>('0');
  public selectedThreadValueSubject$ = this.selectedThreadValueSubject.asObservable();

  private threadsSubject = new BehaviorSubject<Array<ThreadDto>>([]);
  threads$ = this.threadsSubject.asObservable();

  constructor() {
    this.threads = [];
    this.messageMap = new Map<string, Array<MessageDto>>;
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

  updateSelectedValue(newValue: string): void {
    this.selectedThreadValueSubject.next(newValue);
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

    // const newMessage: MessageDto = {
    //   messageDirection: MessageDirection.NEW_MESSAGE_START,
    //   senderId: 'null'
    // };
    // this.messageMap.get(threadId)?.unshift(newMessage);
    this.messageMap.get(threadId)?.unshift(message);
  }
}
