import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalContactsComponent } from './global-contacts.component';

describe('GlobalContactsComponent', () => {
  let component: GlobalContactsComponent;
  let fixture: ComponentFixture<GlobalContactsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlobalContactsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GlobalContactsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
