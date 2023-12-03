import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportControllerComponent } from './report-controller.component';

describe('ReportControllerComponent', () => {
  let component: ReportControllerComponent;
  let fixture: ComponentFixture<ReportControllerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportControllerComponent]
    });
    fixture = TestBed.createComponent(ReportControllerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
