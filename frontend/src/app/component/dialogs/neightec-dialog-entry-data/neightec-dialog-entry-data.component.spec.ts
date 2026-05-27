import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeightecDialogEntryDataComponent } from './neightec-dialog-entry-data.component';

describe('NeightecDialogEntryDataComponent', () => {
  let component: NeightecDialogEntryDataComponent;
  let fixture: ComponentFixture<NeightecDialogEntryDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NeightecDialogEntryDataComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NeightecDialogEntryDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
