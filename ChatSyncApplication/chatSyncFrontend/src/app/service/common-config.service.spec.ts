import { TestBed } from '@angular/core/testing';

import { CommonConfigService } from './common-config.service';

describe('CommonConfigService', () => {
  let service: CommonConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommonConfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
