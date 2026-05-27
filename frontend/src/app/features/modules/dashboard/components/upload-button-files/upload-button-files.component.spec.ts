import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadButtonFilesComponent } from './upload-button-files.component';

describe('UploadButtonFilesComponent', () => {
  let component: UploadButtonFilesComponent;
  let fixture: ComponentFixture<UploadButtonFilesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UploadButtonFilesComponent]
    });
    fixture = TestBed.createComponent(UploadButtonFilesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
