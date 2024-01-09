import { TestBed } from '@angular/core/testing';

import { I18nHrserviceService } from './i18n-hrservice.service';

describe('I18nHrserviceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: I18nHrserviceService = TestBed.get(I18nHrserviceService);
    expect(service).toBeTruthy();
  });
});
