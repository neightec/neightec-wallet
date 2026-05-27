import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardOverviewComponent } from './dashboard-overview.component';
import { FileItem } from 'src/app/models/file-item.model';

describe('DashboardOverviewComponent', () => {
  let component: DashboardOverviewComponent;
  let fixture: ComponentFixture<DashboardOverviewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DashboardOverviewComponent]
    });
    fixture = TestBed.createComponent(DashboardOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('deleteFile', () => {
    it('should remove the file from addFiles array', () => {
      // Mock data for the addFiles array
      const fileItem1 = { name: 'file1.txt' } as FileItem;
      const fileItem2 = { name: 'file2.txt' } as FileItem;
      component.addFiles = [fileItem1, fileItem2];
  
      // Call the deleteFile method
      component.deleteFile(fileItem1);
  
      // Assert that fileItem1 is removed and fileItem2 remains
      expect(component.addFiles).toEqual([fileItem2]);
    });
  
    it('should do nothing if the file is not in the array', () => {
      // Mock data for the addFiles array
      const fileItem1 = { name: 'file1.txt' } as FileItem;
      const fileItem2 = { name: 'file2.txt' } as FileItem;
      const nonExistentFile = { name: 'non-existent.txt' } as FileItem;
      component.addFiles = [fileItem1, fileItem2];
  
      // Call the deleteFile method with a non-existent file
      component.deleteFile(nonExistentFile);
  
      // Assert that the array is unchanged
      expect(component.addFiles).toEqual([fileItem1, fileItem2]);
    });
  });

});


