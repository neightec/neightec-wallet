import { HttpClient } from '@angular/common/http';
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

  public fetchAllAccounts(): Observable<AccountDTO[]> {
    return this.http.get<AccountDTO[]>(`${this.apiEndpoint}account/all`);
  }
}
