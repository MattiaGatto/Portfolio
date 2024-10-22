import { TestBed } from '@angular/core/testing';

import { UserAuthGuard } from './UserAuthGuard.service';

describe('UserService', () => {
  let service: UserAuthGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserAuthGuard);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
