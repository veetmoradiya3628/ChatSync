import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { MessageDto } from 'src/app/models/message_dto.model';
import { ThreadDto } from 'src/app/models/thread_dto.model';

@Injectable({
  providedIn: 'root'
})
export class UserChatCommonServiceService {
  private selectedThreadValueSubject = new BehaviorSubject<string>('0');
  private messageMap: Map<string, Array<MessageDto>>;
  public threads: Array<ThreadDto>;
  public selectedThreadValueSubject$ = this.selectedThreadValueSubject.asObservable();

  constructor() {
    this.threads = [];
    this.messageMap = new Map<string, Array<MessageDto>>;
  }

  // utility methods for threads

  addNewThread(thread: ThreadDto){
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

  findThreadById(threadId: string): ThreadDto | undefined {
    return this.threads.find((thread) => thread.threadId === threadId);
  }

  getAllThreads(): Array<ThreadDto> {
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
}
