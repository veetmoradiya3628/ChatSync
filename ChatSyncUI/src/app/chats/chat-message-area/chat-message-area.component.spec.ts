import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatMessageAreaComponent } from './chat-message-area.component';

describe('ChatMessageAreaComponent', () => {
  let component: ChatMessageAreaComponent;
  let fixture: ComponentFixture<ChatMessageAreaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChatMessageAreaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatMessageAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
