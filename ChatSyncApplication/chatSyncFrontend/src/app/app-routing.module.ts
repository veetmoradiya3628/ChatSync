import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { ChatsComponent } from './chat/chats/chats.component';
import { GroupsComponent } from './group/groups/groups.component';
import { ContactsComponent } from './contact/contacts/contacts.component';
import { ProfileComponent } from './common/profile/profile.component';
import { LogoutComponent } from './common/logout/logout.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/chat',
    pathMatch: 'full'
  },
  {
    path: 'chat',
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
    children: []
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
