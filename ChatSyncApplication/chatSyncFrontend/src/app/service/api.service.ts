import { HttpClient, HttpHeaders } from '@angular/common/http';
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

  // activate urer API
  public activateUser(userId: string, token: string): Observable<any> {
    const url = this.BASE_URL + '/user/activate-account/' + userId + '/' + token;
    return this.http.post(url, null);
  }

  // request activation mail API
  public requestActivationDetails(emailId: string): Observable<any> {
    const url = this.BASE_URL + '/user/request-activation/' + emailId;
    return this.http.post(url, null);
  }

  // get global contacts API
  public getGlobalContactsForUser(userId: string): Observable<any> {
    const url = this.BASE_URL + '/contact/global-contacts/' + userId;
    return this.http.get(url);
  }

  // get contacts for user API
  public getContactsForUser(userId: string): Observable<any> {
    const url = this.BASE_URL + '/contact/' + userId;
    return this.http.get(url);
  }

  // public get user info by userId
  public getUserDetails(userId: string) {
    const url = this.BASE_URL + '/user/info/' + userId;
    return this.http.get(url);
  }

  // get groups for user
  public getGroupsForUserAPI(userId: string) {
    const url = this.BASE_URL + '/group/getGroupsForUser/' + userId;
    return this.http.get(url);
  }

  // get group information by groupId
  public getGroupInformationByGroupId(groupId: string) {
    const url = this.BASE_URL + '/group/' + groupId;
    return this.http.get(url);
  }

  // add user as contact
  public addUserToContactAPI(reqObj: any) {
    const url = this.BASE_URL + '/contact/add-contact';
    return this.http.post(url, reqObj);
  }

  // delete contact from user's contact list
  public deleteUserFromContact(reqObj: any) {
    const url = this.BASE_URL + '/contact/delete-contact';
    return this.http.post(url, reqObj);
  }

  // get global users for group
  public getGlobalUsersForGroup(groupId: any) {
    const url = this.BASE_URL + '/group/getUserNotPartOfGroup/' + groupId;
    return this.http.get(url);
  }

  // create group API
  public createGroupAPI(reqObj: any): Observable<any> {
    const url = this.BASE_URL + '/group/create-group';
    return this.http.post(url, reqObj);
  }

  // get threads API
  public getThreadsForUser(userId: string): Observable<any> {
    const url = this.BASE_URL + '/thread/get-threads/' + userId;
    return this.http.get(url);
  }

  // upload profile picture for user
  public uploadProfilePicture(file: File, userId: string): Observable<any> {

    const url = this.BASE_URL + '/files/upload/profilePicture';

    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    formData.append('userId', userId);

    return this.http.post(url, formData);
  }
}
