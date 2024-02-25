import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UserDto } from 'src/app/models/user_dto.model';
import { ApiService } from 'src/app/service/api.service';
import { AuthService } from 'src/app/service/auth.service';
import { GeneralService } from 'src/app/service/general.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  selectedFile: File | null = null;
  previewImageUrl: string | ArrayBuffer | null = null;
  
  public user_details: UserDto = {};
  public userId!: string;
  public updateUserPasswordForm!: FormGroup;

  constructor(private _userService: AuthService,
    private _generalService: GeneralService,
    private _apiService: ApiService) { }

  ngOnInit(): void {
    this.userId = this._userService.getUserId();
    this.loadUserDetails();
    this.updateUserPasswordForm = new FormGroup({
      currentPassword: new FormControl('', Validators.required),
      newPassword: new FormControl('', Validators.required),
      confirmPassword: new FormControl('', Validators.required)
    })
  }

  loadUserDetails() {
    this._apiService.getUserDetails(this.userId).subscribe(
      (res: any) => {
        this.user_details = res.data;
        console.log(this.user_details)
      },
      (error: any) => {
        console.log(error)
      }
    )
  }

  updateUserPassword() {
    if (this.updateUserPasswordForm.valid) {
      // const requestObj = this.updateUserPasswordForm.value as ResetPassword;
      // if (requestObj.newPassword !== requestObj.confirmPassword) {
      //   this._generalService.openSnackBar('New Password and Confirm password should match!!', 'Ok')
      //   return;
      // }
      // this._generalService.resetUserPassword(this.userId, requestObj).subscribe(
      //   (res: any) => {
      //     console.log(res);
      //     this._generalService.openSnackBar(res.message, 'Ok');
      //     this.updateUserPasswordForm.reset();
      //   },
      //   (error: any) => {
      //     console.log(error)
      //     this._generalService.openSnackBar(error.message, 'Ok')
      //   }
      // )
    } else {
      this._generalService.openSnackBar('Invalid reset password form!!', 'Ok')
      return;
    }
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    // Assuming a file is selected
    if (file) {
      this.selectedFile = file;
      // If you want to preview the image immediately, you can create a FileReader
      const reader = new FileReader();
      reader.onload = (e: any) => {
        // 'e.target.result' contains the data URL which can be used for preview
        this.previewImageUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  clearFile() {
    this.selectedFile = null;
    this.previewImageUrl = null;
  }

  uploadProfilePicture(){
    console.log(`upload profile picture called...`)
  }
}
