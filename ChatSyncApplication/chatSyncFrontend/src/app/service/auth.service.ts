import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public loginStatusSubject = new Subject<boolean>();

  TOKEN_KEY: string = 'token';
  USER_KEY: string = 'user';
  USER_ID_KEY: string = 'userId';
  USER_NAME: string = 'username';
  USER_EMAIL: string = 'email';

  constructor() { }

  public setLoginUserToken(token: string): boolean {
    localStorage.setItem(this.TOKEN_KEY, token);
    return true;
  }

  public setLoginUserDetails(user: any) {
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
  }

  public setUserName(username: string) {
    localStorage.setItem(this.USER_NAME, username);
  }

  public setUserId(userId: any) {
    localStorage.setItem(this.USER_ID_KEY, userId);
  }

  public setEmail(email: any) {
    localStorage.setItem(this.USER_EMAIL, email);
  }

  public getUserId(){
    return localStorage.getItem(this.USER_ID_KEY) || '';
  }

  public isLoggedIn(): boolean {
    let tokenStr = localStorage.getItem(this.TOKEN_KEY);
    let userName = localStorage.getItem(this.USER_NAME);
    let userEmail = localStorage.getItem(this.USER_EMAIL);
    let userData = localStorage.getItem(this.USER_KEY);
    let userId = localStorage.getItem(this.USER_ID_KEY);
    return (
      tokenStr !== null &&
      userName !== null &&
      userEmail !== null &&
      userData !== null &&
      userId !== null &&
      tokenStr !== '' &&
      userName !== '' &&
      userEmail !== '' &&
      userData !== '' &&
      userId !== ''
    );
  }

  public getUser() {
    let userStr = localStorage.getItem('user');
    if (userStr != null) {
      return JSON.parse(userStr);
    } else {
      this.logout();
      return null;
    }
  }

  public getUserRole() {
    let user = this.getUser();
    return user.authorities[0].authority;
  }

  public getUserEmail() {
    return localStorage.getItem(this.USER_EMAIL)
  }

  public logout(): boolean {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    return true;
  }

  public getToken() {
    return localStorage.getItem(this.TOKEN_KEY);
  }
}
