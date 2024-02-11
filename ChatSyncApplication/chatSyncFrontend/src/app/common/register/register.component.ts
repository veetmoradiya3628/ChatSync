import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user.model';
import { ApiService } from 'src/app/service/api.service';
import { GeneralService } from 'src/app/service/general.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registrationForm!: FormGroup;

  constructor(private _fb: FormBuilder, private _apiService: ApiService, private _generalService: GeneralService) {
    this.registrationForm = this._fb.group({
      username: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(32)]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(32)]],
      email: ['', [Validators.required, Validators.email]],
      firstName: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(32)]],
      lastName: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(32)]],
      phoneNo: ['', [Validators.pattern('^\\d{10}$')]],
      roles: [['ROLE_USER']]
    });
  }

  ngOnInit(): void {
  }

  onUserRegistration() {
    console.log(this.registrationForm.value);
    let reqObj = this.registrationForm.value as User;
    console.log(reqObj);

    this._apiService.registerUser(reqObj).subscribe(
      (res: any) => {
        console.log(res);
        this._generalService.openSnackBar(res.message, 'Ok');
        this.resetRegistrationForm();
      },
      (error: any) => {
        console.log(error)
        this._generalService.openSnackBar(error.error.message, 'Ok');
      }
    )

  }

  resetRegistrationForm() {
    this.registrationForm.reset({
      roles: ['ROLE_USER'] // Set default values if needed
    });
    console.log(this.registrationForm.value);
  }

}
