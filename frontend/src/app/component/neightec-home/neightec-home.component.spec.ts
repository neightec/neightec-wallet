import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeightecHomeComponent } from './neightec-home.component';

describe('NeightecHomeComponent', () => {
  let component: NeightecHomeComponent;
  let fixture: ComponentFixture<NeightecHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NeightecHomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NeightecHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
