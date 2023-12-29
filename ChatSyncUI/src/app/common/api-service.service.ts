import { Injectable } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {
  private BASE_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  // sign in
  public signIn(reqObj: any) : Observable<any> {
    const url = this.BASE_URL + '/api/auth/signin'
    return this.http.post(url, reqObj);
  }

}
