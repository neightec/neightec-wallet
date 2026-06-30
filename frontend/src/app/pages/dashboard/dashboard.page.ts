import { Component } from '@angular/core';
import { MatDivider } from '@angular/material/divider';
import { MatTabsModule } from '@angular/material/tabs';
import { HeaderComponent } from 'src/app/component/header/header.component';
import { DashboardComponent } from 'src/app/component/dashboard/dashboard.component';

@Component({
  selector: 'neightec-dashboard-page',
  standalone: true,
  imports: [
    HeaderComponent,
    MatDivider,
    MatTabsModule,
    DashboardComponent
  ],
  templateUrl: './dashboard.page.html',
  styleUrl: './dashboard.page.scss'
})
export class DashboardPage {

}
