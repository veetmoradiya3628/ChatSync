import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './common/home-page/home-page.component';
import { LoginComponent } from './common/login/login.component';
import { RegisterComponent } from './common/register/register.component';
import { UserHomePageComponent } from './user/user-home-page/user-home-page.component';
import { UserChatComponent } from './user/user-chat/user-chat.component';
import { UserGroupsComponent } from './user/user-groups/user-groups.component';
import { UserContactComponent } from './user/user-contact/user-contact.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { ActivateRequestComponent } from './common/activate-request/activate-request.component';
import { ActivateAccountComponent } from './common/activate-account/activate-account.component';

const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full'
  },
  {
    path: 'registration',
    component: RegisterComponent,
    pathMatch: 'full'
  },
  {
    path: 'activate-request',
    component: ActivateRequestComponent,
    pathMatch: 'full'
  },
  {
    path: 'activate-account/:mailId/:activationToken',
    component: ActivateAccountComponent,
    pathMatch: 'full'
  },
  {
    path: 'user',
    component: UserHomePageComponent,
    children: [
      { path: 'chat', component: UserChatComponent },
      { path: 'contact', component: UserContactComponent },
      { path: 'group', component: UserGroupsComponent },
      { path: 'profile', component: UserProfileComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
