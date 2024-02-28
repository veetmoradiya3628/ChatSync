import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactGroupInfoComponent } from './contact-group-info.component';

describe('ContactGroupInfoComponent', () => {
  let component: ContactGroupInfoComponent;
  let fixture: ComponentFixture<ContactGroupInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContactGroupInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContactGroupInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
