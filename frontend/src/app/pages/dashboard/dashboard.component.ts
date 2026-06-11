import { Component } from '@angular/core';
import { HeaderComponent } from 'src/app/component/header/header.component';

@Component({
  selector: 'neightec-dashboard',
  imports: [
    HeaderComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

}
