import { TestBed } from '@angular/core/testing';

import { BookShopService } from './book-shop.service';

describe('BookShopService', () => {
  let service: BookShopService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookShopService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
