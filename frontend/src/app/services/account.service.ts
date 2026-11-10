import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { neightEnvironment } from 'src/environments/environment';
import { AccountDTO } from '../dto/accountDTO';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private readonly apiEndpoint = `${neightEnvironment.api_url}`;

  constructor(private http: HttpClient) {}

  /**
   * Without a session ID the backend falls back to com.neightec.server.temp.sessionID.
   */
  public fetchAccountsBySessionID(sessionID: string | null): Observable<AccountDTO[]> {
    const url = `${this.apiEndpoint}wallet-account/get-accounts-by-session-id`;
    const params = sessionID ? new HttpParams().set('sessionId', sessionID) : new HttpParams();
    return this.http.get<AccountDTO[]>(url, { params });
  }
}
