import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatsComponent } from './chat/chats/chats.component';
import { GroupsComponent } from './group/groups/groups.component';
import { ContactsComponent } from './contact/contacts/contacts.component';
import { ProfileComponent } from './common/profile/profile.component';
import { LogoutComponent } from './common/logout/logout.component';
import { LoginComponent } from './common/login/login.component';
import { RegisterComponent } from './common/register/register.component';
import { HomePageComponent } from './common/home-page/home-page.component';

const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
    pathMatch: 'full'
  },
  {
    path: 'chats',
    component: ChatsComponent,
    children: []
  },
  {
    path: 'group',
    component: GroupsComponent,
    children: []
  },
  {
    path: 'contact',
    component: ContactsComponent,
    children: []
  },
  {
    path: 'profile',
    component: ProfileComponent,
    children: []
  },
  {
    path: 'logout',
    component: LogoutComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
