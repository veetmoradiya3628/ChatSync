import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class UserChatCommonServiceService {
  private selectedThreadValueSubject = new BehaviorSubject<string>('0');

  selectedThreadValueSubject$ = this.selectedThreadValueSubject.asObservable();

  updateSelectedValue(newValue: string): void {
    this.selectedThreadValueSubject.next(newValue);
  }
}
