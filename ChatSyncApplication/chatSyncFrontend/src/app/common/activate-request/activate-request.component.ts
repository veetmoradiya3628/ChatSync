import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/service/api.service';
import { GeneralService } from 'src/app/service/general.service';

@Component({
  selector: 'app-activate-request',
  templateUrl: './activate-request.component.html',
  styleUrls: ['./activate-request.component.css']
})
export class ActivateRequestComponent implements OnInit {

  activateReqForm!: FormGroup;
  
  constructor(private fb: FormBuilder, private _apiService: ApiService, private _generalService: GeneralService) { }

  ngOnInit(): void {
    this.activateReqForm = this.fb.group({
      email: ["", [Validators.required, Validators.email]],
    })
  }

  onactivateReqForm(){
    console.log(this.activateReqForm.value);
    let emailId = this.activateReqForm.value['email'];

    this._apiService.requestActivationDetails(emailId).subscribe(
      (res: any) => {
        console.log(res);
        this._generalService.openSnackBar(res.message, 'Ok');
        this.activateReqForm.reset();
      },
      (error: any) => {
        console.log(error)
        this._generalService.openSnackBar(error.error.message, 'Ok');
      }
    )

  }

}
