import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopularFoodComponent } from './popular-food.component';

describe('PopularFoodComponent', () => {
  let component: PopularFoodComponent;
  let fixture: ComponentFixture<PopularFoodComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PopularFoodComponent]
    });
    fixture = TestBed.createComponent(PopularFoodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
