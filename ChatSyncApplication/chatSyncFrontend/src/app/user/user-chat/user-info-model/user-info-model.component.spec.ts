import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserInfoModelComponent } from './user-info-model.component';

describe('UserInfoModelComponent', () => {
  let component: UserInfoModelComponent;
  let fixture: ComponentFixture<UserInfoModelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserInfoModelComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserInfoModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
