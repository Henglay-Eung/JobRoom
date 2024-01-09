import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignUpHrComponent } from './sign-up-hr.component';

describe('SignUpHrComponent', () => {
  let component: SignUpHrComponent;
  let fixture: ComponentFixture<SignUpHrComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignUpHrComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignUpHrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
