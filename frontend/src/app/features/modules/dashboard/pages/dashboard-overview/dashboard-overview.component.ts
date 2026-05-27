import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Store } from '@ngrx/store';
import { ModalService } from 'carbon-components-angular';
import { GuestWeddingListService } from 'src/app/services/guest-wedding-list.service';
import { RegisterGuestDialogComponent } from '../../components/register-guest-dialog/register-guest-dialog.component';
import { FileItem } from 'src/app/models/file-item.model';
import { addDashboardFiles, uploadDashboardFiles } from '../../store/dashboard.actions';

@Component({
  selector: 'neight-tech-dashboard-overview',
  templateUrl: './dashboard-overview.component.html',
  styleUrls: ['./dashboard-overview.component.scss'],
  standalone: false
})
export class DashboardOverviewComponent implements OnInit {

  addFiles: FileItem[] = [];

  dragOver: boolean = false;
  accept = ['.pdf', '.doc', '.docx'];

  private maximumFileSize = 5 * 1024 * 1024;

  constructor(
    private store: Store,
    protected guestWeddingListService: GuestWeddingListService,
    protected modalService: ModalService
  ) {}

  ngOnInit(): void {
    console.log("Init Dashboard Overview");
  }

  onDragOver(event: any) {
    event.stopPropagation();
    event.preventDefault();

    this.dragOver = true;
  }
  
  onDragLeave(event: any) {
    event.stopPropagation();
    event.preventDefault();
    
    this.dragOver = false;
  }

  registerGuestManuallyPopup(): void {
    this.modalService.create({
      component: RegisterGuestDialogComponent,
      inputs: {
        modalText: "Hello universe.",
        newInput: true
      }
    });
  }

  createFileItem(file: File): FileItem {
    const fileItem: FileItem = {
      id: null,
      uploaded: false,
      state: 'edit',
      invalid: false,
      invalidSize: false,
      name: file.name,
      file: file,
    };

    if (file.size > this.maximumFileSize) {
      fileItem.invalidSize = true;
    }

    return fileItem;
  }

  onDrop(event: any) {
    event.stopPropagation();
    event.preventDefault();

    const valid: boolean = this.checkFileAcceptenceOnDrag(event,  this.accept);

    Array.from(event.dataTransfer.files).forEach((file: any) => {
      this.checkDuplicationInQueue(file, this.addFiles);
    });

    if (this.dragOver) {
      this.dragOver = !this.dragOver;
    }
  }

  // TODO the implementation
  checkFileAcceptenceOnDrag(event: any, acceptedFormats: string[]): boolean {
    const files: any = event.dataTransfer?.files;

    if (files && files.length !== 0) {
      const file = files[0];
      const fileExtension = file?.name?.split('.')[1];
      const valid = fileExtension ? acceptedFormats.indexOf(`.${fileExtension}`) : -1;
      return valid !== -1;
    } else {
      return false;
    }
  }

  addDashboardFiles(files: FileItem[]): void {
    this.store.dispatch(addDashboardFiles({files}));
  }

  private checkDuplicationInQueue(fileToAdd: FileItem, files: FileItem[]): void {
    const fileNames = new Set(files.map(file => file.name));
  
    if (!fileNames.has(fileToAdd.name)) {
      files.push(fileToAdd);
    }
  }

  uploadFiles(): void {
    if (this.addFiles) {
      this.store.dispatch(uploadDashboardFiles({files: this.addFiles}));
    }
  }

  deleteFile(fileItem: FileItem): void {
    this.addFiles = this.addFiles.filter(file => file.name !== fileItem.name);
  }
}
