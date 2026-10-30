import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { NeightApiService } from 'src/neight-api.service';
import { neightEnvironment } from 'src/environments/environment';
import { WalletTransactionDTO } from '../dto/walletTransactionDTO';
import { WalletTransactionChartDataDTO } from '../dto/walletTransactionChartDataDTO';

@Injectable({
  providedIn: 'root'
})
export class WalletTransactionService {

  apiEndpoint: string;
  public path: string = "wallet-transaction";

  constructor(protected injector: Injector,
    protected neightApi: NeightApiService,
    protected http: HttpClient) {
      this.apiEndpoint = `${neightEnvironment.api_url}`;
}


  public fetchAllWalletTransaction(): Observable<WalletTransactionDTO[]> {
    try {
      const url = `${this.apiEndpoint}wallet-transaction/all`;
      const httpHeaders = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
      return this.http.get<WalletTransactionDTO[]>(url, {headers: httpHeaders});
    } catch (e) {
      throw e;
    }
  }  
  
  public fetchAllWalletTransactionBySessionID(sessionID: string): Observable<WalletTransactionChartDataDTO> {
    try {
      const url = `${this.apiEndpoint}wallet-transaction/get-transaction-balance-chart`;
      const params = new HttpParams().set('sessionId', sessionID);
      return this.http.get<WalletTransactionChartDataDTO>(url, { params });
    } catch (e) {
      throw e;
    }
  }
}
