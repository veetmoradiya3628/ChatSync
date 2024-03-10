import { TestBed } from '@angular/core/testing';

import { UserChatCommonServiceService } from './user-chat-common-service.service';

describe('UserChatCommonServiceService', () => {
  let service: UserChatCommonServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserChatCommonServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
