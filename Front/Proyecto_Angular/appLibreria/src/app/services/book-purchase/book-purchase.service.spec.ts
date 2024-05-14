import { TestBed } from '@angular/core/testing';

import { BookPurchaseService } from './book-purchase.service';

describe('BookPurchaseService', () => {
  let service: BookPurchaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookPurchaseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
