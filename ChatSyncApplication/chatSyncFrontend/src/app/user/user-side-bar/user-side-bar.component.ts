import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, map, shareReplay } from 'rxjs';
import { WebsocketService } from 'src/app/service/websocket.service';

@Component({
  selector: 'app-user-side-bar',
  templateUrl: './user-side-bar.component.html',
  styleUrls: ['./user-side-bar.component.css']
})
export class UserSideBarComponent implements OnInit, OnDestroy {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );


  constructor(private breakpointObserver: BreakpointObserver, private _wsService: WebsocketService) { }


  ngOnInit(): void {
    this._wsService.connect();

    setTimeout(() => {
      // this._wsService.sentData("random text message : " + Date.now().toString())
    }, 1000)
  }

  ngOnDestroy(): void {
    this._wsService.disconnect();
  }


  logoutUser() {
    throw new Error('Method not implemented.');
  }
}
