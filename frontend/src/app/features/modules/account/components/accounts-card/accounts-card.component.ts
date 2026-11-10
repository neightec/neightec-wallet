import { Component, input } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { AccountDTO } from 'src/app/dto/accountDTO';

@Component({
  selector: 'neightec-accounts-card',
  imports: [MatCardModule, DecimalPipe],
  templateUrl: './accounts-card.component.html',
  styleUrl: './accounts-card.component.scss'
})
export class AccountsCardComponent {
  account = input.required<AccountDTO>();
}
