import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavHeaderComponent } from './common/nav-header/nav-header.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ChatsComponent } from './chat/chats/chats.component';
import { ContactsComponent } from './contact/contacts/contacts.component';
import { GroupsComponent } from './group/groups/groups.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { ProfileComponent } from './common/profile/profile.component';
import { LogoutComponent } from './common/logout/logout.component';
import { LoginComponent } from './common/login/login.component';
import { RegisterComponent } from './common/register/register.component';
import { HomePageComponent } from './common/home-page/home-page.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ChatThreadComponent } from './chat/chat-thread/chat-thread.component';

@NgModule({
  declarations: [
    AppComponent,
    NavHeaderComponent,
    ChatsComponent,
    ContactsComponent,
    GroupsComponent,
    ProfileComponent,
    LogoutComponent,
    LoginComponent,
    RegisterComponent,
    HomePageComponent,
    ChatThreadComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    ScrollingModule,
    MatFormFieldModule,
    MatInputModule,
    FlexLayoutModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
