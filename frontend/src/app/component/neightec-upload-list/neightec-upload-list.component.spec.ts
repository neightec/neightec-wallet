import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeightecUploadListComponent } from './neightec-upload-list.component';

describe('NeightecUploadListComponent', () => {
  let component: NeightecUploadListComponent;
  let fixture: ComponentFixture<NeightecUploadListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NeightecUploadListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NeightecUploadListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
