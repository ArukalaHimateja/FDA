import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BecomeAPartnerComponent } from './become-apartner.component';

describe('BecomeAPartnerComponent', () => {
  let component: BecomeAPartnerComponent;
  let fixture: ComponentFixture<BecomeAPartnerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BecomeAPartnerComponent]
    });
    fixture = TestBed.createComponent(BecomeAPartnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
