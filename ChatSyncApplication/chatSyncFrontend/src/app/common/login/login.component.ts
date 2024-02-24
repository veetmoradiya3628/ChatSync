import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { GeneralService } from 'src/app/service/general.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  hide = true;
  loginForm !: FormGroup;

  constructor(private fb: FormBuilder,
    private _apiService: ApiService,
    private _generalService: GeneralService,
    private _authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {

    if (this._authService.isLoggedIn()) {
      console.log(`local data available...`)
      let userRole = this._authService.getUserRole();
      if (userRole === 'ROLE_USER') {
        this.router.navigate(['/user/chat']);
      } else {
        console.log(`${userRole} is invalid. should go for fresh login...`);
      }
    }

    this.loginForm = this.fb.group({
      username: ["", Validators.required],
      password: ["", Validators.required]
    })
  }

  onSubmitLoginForm() {
    console.log(this.loginForm.value);
    let reqObj = this.loginForm.value;
    this._apiService.loginUser(reqObj).subscribe(
      (res: any) => {
        console.log(res);

        this._authService.setLoginUserToken(res.token);
        this._authService.setUserId(res.userDetails.userId);
        this._authService.setLoginUserDetails(res.userDetails);
        this._authService.setUserName(res.userDetails.username);
        this._authService.setEmail(res.userDetails.email);

        let userRole = res.userDetails.authorities[0].authority;
        console.log(`userRole : ${userRole}`)
        if (userRole != null && userRole == 'ROLE_USER') {
          this.router.navigate(['/user/chat']);
          this._authService.loginStatusSubject.next(true);
        } else {
          console.log(`role : ${userRole} is not handled yet`)
        }

        this._generalService.openSnackBar('Authenticated successfully!!', 'Ok');
        this.loginForm.reset();
      },
      (error: any) => {
        console.log(error);
        this._generalService.openSnackBar(error.error.message, 'Ok');
      }
    )
  }
}
