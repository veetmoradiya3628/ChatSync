import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserChatMessageThreadComponent } from './user-chat-message-thread.component';

describe('UserChatMessageThreadComponent', () => {
  let component: UserChatMessageThreadComponent;
  let fixture: ComponentFixture<UserChatMessageThreadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserChatMessageThreadComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserChatMessageThreadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
