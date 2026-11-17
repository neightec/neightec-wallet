import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatIcon } from '@angular/material/icon';
import {Store} from "@ngrx/store";
import {addAccount} from "../../../store/account.actions";

@Component({
  selector: 'neightec-accounts-add',
  imports: [MatIcon, ReactiveFormsModule],
  templateUrl: './accounts-add.component.html',
  standalone: true,
  styleUrl: './accounts-add.component.scss'
})
export class AccountsAddComponent {

  formGroupAccountAdd = new FormGroup({
    accountName: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required, Validators.pattern(/\S/),
        Validators.maxLength(50)
      ],
    }),
    accountBalance: new FormControl<number | null>(null, {
      validators: [Validators.required],
    }),
  });
  
  constructor(
    private dialogRef: MatDialogRef<AccountsAddComponent>,
    private store: Store
  ) {}


  closeDialog() {
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.formGroupAccountAdd.invalid) {
      this.formGroupAccountAdd.markAllAsTouched();
      return;
    }

    const { accountName, accountBalance } = this.formGroupAccountAdd.getRawValue();
    this.store.dispatch(addAccount({
      data: {
        name: accountName.trim(),
        balance: String(accountBalance ?? ''),
        currency: "€" }
    }));
    this.dialogRef.close();
  }

}
