import { Component, input, OnInit } from '@angular/core';
import { DecimalPipe, NgIf } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { AccountDTO } from 'src/app/dto/accountDTO';
import { MatIcon, MatIconModule } from "@angular/material/icon";
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AccountsAddComponent } from '../dialogs/accounts-add/accounts-add.component';

@Component({
  selector: 'neightec-accounts-card',
  imports: [
    MatCardModule,
    DecimalPipe,
    NgIf,
    MatIcon,
    MatIconModule
  ],
  templateUrl: './accounts-card.component.html',
  styleUrl: './accounts-card.component.scss'
})
export class AccountsCardComponent implements OnInit {

  constructor(
    public dialog: MatDialog
  ) {}

  account = input.required<AccountDTO>();
  dialogConfiguration = new MatDialogConfig();

  ngOnInit(): void {
    this.dialogConfiguration.disableClose = true;
    this.dialogConfiguration.autoFocus = true;
  }


  accountAdd(): void {
    this.dialog.open(AccountsAddComponent, this.dialogConfiguration);
  }

}
