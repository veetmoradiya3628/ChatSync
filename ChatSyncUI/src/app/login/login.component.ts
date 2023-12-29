import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ApiServiceService} from "../common/api-service.service";
import {GeneralService} from "../common/general.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide = true;
  loginForm !: FormGroup;

  constructor(private fb: FormBuilder,
              private _apiService: ApiServiceService,
              private _generalService: GeneralService){}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ["", Validators.required],
      password: ["", Validators.required]
    })
  }

  onSubmitLoginForm() {
    if (this.loginForm.valid) {
      this._apiService.signIn(this.loginForm.value).subscribe(
        (res : any) => {
          this._generalService.openSnackBar('Logged In successfully!!', 'Ok');
        },
        (error: any) => {
          this._generalService.openSnackBar('Login Failed!!', '');
        }
      )
    }
    this._generalService.openSnackBar('Invalid login form', 'Ok')
    return
  }
}
