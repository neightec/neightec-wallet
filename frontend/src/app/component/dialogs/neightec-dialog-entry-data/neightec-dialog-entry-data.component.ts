import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-neightec-dialog-entry-data',
  templateUrl: './neightec-dialog-entry-data.component.html',
  styleUrl: './neightec-dialog-entry-data.component.scss',
  standalone:false
})
export class NeightecDialogEntryDataComponent {

  constructor(
    public dialogRef: MatDialogRef<NeightecDialogEntryDataComponent>,
  ) {
  }

  onClickClose(): void {
    return this.dialogRef.close(false);
  }
}
