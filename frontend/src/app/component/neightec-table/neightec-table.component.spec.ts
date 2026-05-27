import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeightecTableComponent } from './neightec-table.component';

describe('NeightecTableComponent', () => {
  let component: NeightecTableComponent;
  let fixture: ComponentFixture<NeightecTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NeightecTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NeightecTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
