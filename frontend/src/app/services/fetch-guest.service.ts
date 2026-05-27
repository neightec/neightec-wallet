import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { NeightApiService } from 'src/neight-api.service';
import { NeightEnterMemberDTO } from '../dto/neightMemberEnterDTO';

@Injectable({
  providedIn: 'root'
})
export class FetchGuestService {

  public controller_path: string = "getall_guests";
  public controller_family_path: string = "getall_family";
  public put_controller_path: string = "add_new_guest";
  public get_last_id: string = "get_last_id";

  constructor(protected injector: Injector,
      protected neightApi: NeightApiService,
      protected http: HttpClient) {
  }

  public getAllGuests(): Observable<any> {
    let url: string = this.neightApi.getBackendUrl() + this.controller_path;
    const httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    const response: any = this.http.get(url, {headers: httpHeaders});
    return response;
  }

  public getAllFamily(): Observable<any> {
    let url: string = this.neightApi.getBackendUrl() + this.controller_family_path;
    const httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    const response: any = this.http.get(url, {headers: httpHeaders});
    return response;
  }

  public addNewGuest(data: NeightEnterMemberDTO): Observable<any> {
    let url: string = this.neightApi.getBackendUrl() + this.put_controller_path;
    let httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(url, data, {headers: httpHeaders, observe: "response"});
  }

}
