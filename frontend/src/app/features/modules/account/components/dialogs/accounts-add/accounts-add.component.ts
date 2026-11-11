import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'neightec-accounts-add',
  imports: [MatIcon, ReactiveFormsModule],
  templateUrl: './accounts-add.component.html',
  styleUrl: './accounts-add.component.scss'
})
export class AccountsAddComponent {

  formGroupAccountAdd = new FormGroup({
    accountName: new FormControl(''),
    accountBalance: new FormControl(''),
  });
  
  constructor(
    private dialogRef: MatDialogRef<AccountsAddComponent>
  ) {}


  closeDialog() {
    this.dialogRef.close();
  }
  
  onSubmit() {
    this.dialogRef.close();
  }

}
