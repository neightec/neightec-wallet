import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { NeightApiService } from 'src/neight-api.service';
import { NeightGuestCheckInDTO } from '../dto/neightGuestCheckinDTO';

@Injectable({
  providedIn: 'root'
})
export class CheckingService {

  public controller_path: string = "guest-check-validation";
  public checkin_controller_path: string = "guest-check-in";
  // public checkin_controller_path2: string = "guest-check-in-2";

  constructor(protected injector: Injector,
    protected neightApi: NeightApiService,
    protected http: HttpClient) {
  }

  // check if guest doest exist or not
  // and has been attended or not
  public async checkingGuest(id: string): Promise<any> {
    let url: string = this.neightApi.getBackendUrl() + this.controller_path + "/" + id;
    const httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    const response: any = await this.http.get(url, {headers: httpHeaders}).toPromise();
    return response;
  }

  // let user give the code
  public async checkingGuestCode(name: string, code: number): Promise<any> {
    let url: string = this.neightApi.getBackendUrl() + this.controller_path + "/" + name + "/" + code;
    const httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    const response: any = await this.http.get(url, {headers: httpHeaders}).toPromise();
    return response;
  }

  // contoller checks in guest
  // todo pass hascode
  public async checkingGuestIn(guestDTO: NeightGuestCheckInDTO): Promise<any> {
    let url: string = this.neightApi.getBackendUrl() + this.checkin_controller_path;
    let httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(url, guestDTO, {headers: httpHeaders, observe: "response"}).toPromise();
  }
}
