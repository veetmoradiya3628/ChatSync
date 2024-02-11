import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/service/api.service';
import { GeneralService } from 'src/app/service/general.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  hide = true;
  loginForm !: FormGroup;

  constructor(private fb: FormBuilder, private _apiService: ApiService, private _generalService: GeneralService) { }

  ngOnInit(): void {
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
