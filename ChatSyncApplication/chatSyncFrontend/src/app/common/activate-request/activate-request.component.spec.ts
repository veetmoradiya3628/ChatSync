import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivateRequestComponent } from './activate-request.component';

describe('ActivateRequestComponent', () => {
  let component: ActivateRequestComponent;
  let fixture: ComponentFixture<ActivateRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActivateRequestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActivateRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
