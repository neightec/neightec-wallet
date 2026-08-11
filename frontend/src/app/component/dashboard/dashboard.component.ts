import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

import { mockDashboardPanels } from '../../mock-data/mock-data.helper';

@Component({
  selector: 'neightec-dashboard',
  imports: [
    CommonModule, MatCardModule, MatButtonModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {

  panels: any[] = [];
  
  constructor() { }
  
  ngOnInit(): void {
    this.panels = this.fetchMockDashboardPanels();
  }

  public fetchMockDashboardPanels(): any {
    return mockDashboardPanels;
  }

}
