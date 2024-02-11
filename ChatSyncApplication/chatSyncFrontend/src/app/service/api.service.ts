import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private BASE_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  // user registration API
  public registerUser(reqObj: User): Observable<any> {
    const url = this.BASE_URL + "/user/register-user";
    return this.http.post(url, reqObj);
  }

  // login user API
  public loginUser(reqObj: any): Observable<any> {
    const url = this.BASE_URL + '/generate-token';
    return this.http.post(url, reqObj);
  }
}
