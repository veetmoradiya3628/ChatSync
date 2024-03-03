import { TestBed } from '@angular/core/testing';

import { ContactTabService } from './contact-tab.service';

describe('ContactTabService', () => {
  let service: ContactTabService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContactTabService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
