import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserChatThreadsComponent } from './user-chat-threads.component';

describe('UserChatThreadsComponent', () => {
  let component: UserChatThreadsComponent;
  let fixture: ComponentFixture<UserChatThreadsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserChatThreadsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserChatThreadsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
