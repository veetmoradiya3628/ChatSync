import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.css']
})
export class ActivateAccountComponent implements OnInit {
  emailId!: string;
  activationToken!: string;
  message!: string;
  isFailed: boolean = false;

  constructor(private router: Router, private route: ActivatedRoute, private apiService: ApiService){}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      console.log(params);
      this.emailId = params['mailId'];
      this.activationToken = params['activationToken'];
    });

    this.activateUserRequest();
  }

  activateUserRequest(){
    this.apiService.activateUser(this.emailId, this.activationToken).subscribe(
      (res: any) => {
        console.log(res);
        this.message = res.message;
      },
      (error: any) => {
        console.log(error);
        this.isFailed = true;
        this.message = error.error.message;
      }
    )
  }

}
