import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Chart } from 'chart.js/auto';

import { mockBilanzChartData, mockDashboardAccountPanels, mockDashboardPanels } from '../../mock-data/mock-data.helper';
import { HttpClient } from '@angular/common/http';
import { NeightApiService } from 'src/neight-api.service';
import { WalletTransactionService } from 'src/app/services/wallet-transaction.service';
import { WalletTransactionDTO } from 'src/app/dto/walletTransactionDTO';

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
    protected walletTransaction: WalletTransactionService,
    ) {
  }

  ngOnInit(): void {
    this.panels = this.fetchMockDashboardPanels();
    this.accountPanels = this.fetchMockDashboardAccountPanels();

    this.fetchWalletTransaction().subscribe((transactions: WalletTransactionDTO[]) => {
      console.error("test: ", transactions);
    });
  }

  ngAfterViewInit(): void {
    this.getChartData();
  }

  public fetchMockDashboardPanels(): any {
    return mockDashboardPanels;
  }

  public fetchMockDashboardAccountPanels(): any {
    return mockDashboardAccountPanels;
  }

  private getChartData(): void {
    new Chart(this.bilanzChartRef.nativeElement, {
      type: 'bar',
      data: {
        labels: mockBilanzChartData.labels,
        datasets: [
          {
            label: 'Einnahmen',
            data: mockBilanzChartData.einnahmen,
            borderColor: '#20307f',
            backgroundColor: '#9c75e433',
          },
          {
            label: 'Ausgaben',
            data: mockBilanzChartData.ausgaben,
            borderColor: '#a37b78',
            backgroundColor: '#39040133',
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
      },
    });
  }

  public fetchWalletTransaction(): any {
    return this.walletTransaction.fetchAllWalletTransaction();
  }

}
