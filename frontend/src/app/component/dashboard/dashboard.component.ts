import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Chart } from 'chart.js/auto';

import { mockBilanzChartData, mockDashboardAccountPanels, mockDashboardPanels } from '../../mock-data/mock-data.helper';
import { HttpClient } from '@angular/common/http';
import { NeightApiService } from 'src/neight-api.service';
import { WalletTransactionService } from 'src/app/services/wallet-transaction.service';
import { WalletTransactionChartDataDTO } from 'src/app/dto/walletTransactionChartDataDTO';

@Component({
  selector: 'neightec-dashboard',
  imports: [
    CommonModule, MatCardModule, MatButtonModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit, AfterViewInit {

  @ViewChild('bilanzChart') 
  bilanzChartRef!: ElementRef<HTMLCanvasElement>;

  panels: any[] = [];
  accountPanels: any[] = [];

  constructor(
    protected http: HttpClient,
    protected neightApi: NeightApiService,
    protected walletTransactionService: WalletTransactionService,
    ) {
  }

  ngOnInit(): void {
    this.panels = this.fetchMockDashboardPanels();
    this.accountPanels = this.fetchMockDashboardAccountPanels();

  }

  ngAfterViewInit(): void {
    this.fetchWalletTransactionChartBySessionID('f40f1143-067a-43a5-870f-dde93ef2f069').subscribe({
      next: (data: WalletTransactionChartDataDTO) => {
        this.initChartData(data);
      },
      error: (error) => {
        console.error('Error loading wallet transaction chart data:', error);
      }
    });
  }

  public fetchMockDashboardPanels(): any {
    return mockDashboardPanels;
  }

  public fetchMockDashboardAccountPanels(): any {
    return mockDashboardAccountPanels;
  }

  private initChartData(chartData: WalletTransactionChartDataDTO): void {

    new Chart(this.bilanzChartRef.nativeElement, {
      type: 'line',
      data: {
        labels: chartData.labels,
        datasets: [
          {
            label: 'Einnahmen',
            data: chartData.einnahmen,
            borderColor: '#20307f',
            backgroundColor: '#9c75e433',
          },
          {
            label: 'Ausgaben',
            data: chartData.ausgaben,
            borderColor: '#a37b78',
            backgroundColor: '#39040133',
          },{
            label: 'Ersparnisse',
            data: chartData.ersparnisse,
            borderColor: '#220604',
            backgroundColor: '#59221f33',
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
      },
    });
  }

  /**
   * Get current transaction for balance chart in Dashboard
   * based on sessionID
   * @param sessionID 
   * @returns chart data based on @WalletTransactionChartDataDTO
   */
  public fetchWalletTransactionChartBySessionID(sessionID: string): any {
    return this.walletTransactionService.fetchAllWalletTransactionBySessionID(sessionID);
  }

}
