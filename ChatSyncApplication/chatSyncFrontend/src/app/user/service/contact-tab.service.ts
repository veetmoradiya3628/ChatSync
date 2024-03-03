import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';

@Injectable({
  providedIn: 'root'
})
export class ContactTabService {
  private tabChangeSource = new Subject<number>();

  // Observable for components to subscribe to
  tabChanged$ = this.tabChangeSource.asObservable();

  // Method to be called by the tab-group when the selected tab changes
  tabChanged(index: number): void {
    this.tabChangeSource.next(index);
  }
}
