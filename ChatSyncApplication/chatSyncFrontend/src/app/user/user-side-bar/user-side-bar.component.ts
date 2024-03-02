import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, map, shareReplay } from 'rxjs';
import { AuthService } from 'src/app/service/auth.service';
import { ConfirmationDialogService } from 'src/app/service/confirmation-dialog.service';
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


  constructor(private breakpointObserver: BreakpointObserver, 
              private _wsService: WebsocketService,
              private _dialogConfirmationService: ConfirmationDialogService,
              private _authService: AuthService,
              private _router: Router) { }


  ngOnInit(): void {
    this._wsService.connect();
  }

  ngOnDestroy(): void {
    this._wsService.disconnect();
  }


  logoutUser() {
    this._dialogConfirmationService.openConfirmationDialog('Are you sure want to logout ?').then((result) => {
      if (result) {
        this._authService.logout()
        this._router.navigateByUrl('/login')
      } else {
        return;
      }
    })
  }
}
