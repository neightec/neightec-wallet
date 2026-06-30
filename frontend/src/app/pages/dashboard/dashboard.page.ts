import { Component } from '@angular/core';
import { MatDivider } from '@angular/material/divider';
import { MatTabsModule } from '@angular/material/tabs';
import { HeaderComponent } from 'src/app/component/header/header.component';

@Component({
  selector: 'neightec-dashboard',
  imports: [
    HeaderComponent,
    MatDivider,
    MatTabsModule
  ],
  templateUrl: './dashboard.page.html',
  styleUrl: './dashboard.page.scss'
})
export class DashboardPage {

}
