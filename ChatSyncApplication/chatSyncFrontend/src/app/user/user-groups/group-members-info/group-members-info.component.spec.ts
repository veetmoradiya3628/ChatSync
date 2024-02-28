import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupMembersInfoComponent } from './group-members-info.component';

describe('GroupMembersInfoComponent', () => {
  let component: GroupMembersInfoComponent;
  let fixture: ComponentFixture<GroupMembersInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupMembersInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GroupMembersInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
