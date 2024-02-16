import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTabsModule } from '@angular/material/tabs';
import { MatInputModule } from '@angular/material/input';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { LoginComponent } from './common/login/login.component';
import { RegisterComponent } from './common/register/register.component';
import { HomePageComponent } from './common/home-page/home-page.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './common/header/header.component';
import { UserHomePageComponent } from './user/user-home-page/user-home-page.component';
import { UserSideBarComponent } from './user/user-side-bar/user-side-bar.component';
import { UserChatComponent } from './user/user-chat/user-chat.component';
import { UserContactComponent } from './user/user-contact/user-contact.component';
import { UserGroupsComponent } from './user/user-groups/user-groups.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { UserChatThreadsComponent } from './user/user-chat/user-chat-threads/user-chat-threads.component';
import { UserChatMessageThreadComponent } from './user/user-chat/user-chat-message-thread/user-chat-message-thread.component';
import { UserInfoModelComponent } from './user/user-chat/user-info-model/user-info-model.component';
import { ActivateRequestComponent } from './common/activate-request/activate-request.component';
import { ActivateAccountComponent } from './common/activate-account/activate-account.component';
import { ContactsComponent } from './user/user-contact/contacts/contacts.component';
import { GlobalContactsComponent } from './user/user-contact/global-contacts/global-contacts.component';
import { GroupsComponent } from './user/user-groups/groups/groups.component';
import { CreateNewGroupComponent } from './user/user-groups/create-new-group/create-new-group.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomePageComponent,
    HeaderComponent,
    UserHomePageComponent,
    UserSideBarComponent,
    UserChatComponent,
    UserContactComponent,
    UserGroupsComponent,
    UserProfileComponent,
    UserChatThreadsComponent,
    UserChatMessageThreadComponent,
    UserInfoModelComponent,
    ActivateRequestComponent,
    ActivateAccountComponent,
    ContactsComponent,
    GlobalContactsComponent,
    GroupsComponent,
    CreateNewGroupComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    MatSnackBarModule,
    ScrollingModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    MatTooltipModule,
    MatTabsModule,
    HttpClientModule,
    FlexLayoutModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
