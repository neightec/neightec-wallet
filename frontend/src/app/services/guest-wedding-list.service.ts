import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { NeightApiService } from 'src/neight-api.service';
// import { mockGuests } from '../mock-data/mock-data.helper';
import { neightEnvironment } from 'src/environments/environment';
import { GuestDTO } from '../dto/GuestDTO';
import { FileItem } from '../models/file-item.model';

@Injectable({
  providedIn: 'root'
})
export class GuestWeddingListService {

  apiEndpoint: string;
  public path: string = "guest";

  constructor(protected injector: Injector,
    protected neightApi: NeightApiService,
    protected http: HttpClient) {
      this.apiEndpoint = `${neightEnvironment.api_url}`;
}


  public fetchWeddingDashboardList(): Observable<GuestDTO[]> {
    try {
      const url = `${this.apiEndpoint}guest/get-dashboard-list`;
      const httpHeaders = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
      return this.http.get<GuestDTO[]>(url, {headers: httpHeaders});
    } catch (e) {
      throw e;
    }
  }

  public enterGuestToWeddingListManual(guests: string[]): Observable<GuestDTO[]> {
    try {
      const url = `${this.apiEndpoint}guest/add-list-manual`;
      const body = JSON.stringify(guests);
      const httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
      return this.http.post<GuestDTO[]>(url, body, {headers: httpHeaders});
    } catch (e) {
      throw e;
    }
  }

  enterGuestToWeddingListUpload(files: any): Observable<GuestDTO[]> {
    const url = `${this.apiEndpoint}guest/add-list-upload`;
    const formData = new FormData();
    files.forEach(file => {
      formData.append('files', file);
    });
    return this.http.post<GuestDTO[]>(url, formData);
}

  deleteGuests(guestLists: string[]): Observable<GuestDTO[]> {
    try {
      const url = `${this.apiEndpoint}guest/delete-guests-by-fullnames`;
      const body = JSON.stringify(guestLists);
      const httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
      return this.http.post<GuestDTO[]>(url, body, {headers: httpHeaders});
    } catch (e) {
      throw e;
    }
  }

  validateUniqueGuestFullname(fullName: string): Observable<boolean> {
    try {
      const url = `${this.apiEndpoint}guest/is-guest-full-name-unique?full_name=${fullName}`
      const httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
      return this.http.get<boolean>(url, {headers: httpHeaders});
    } catch (e) {
      throw e;
    }
  }

  public uploadGuestFromFiles(guests: string[]): Observable<GuestDTO[]> {
    try {
      const url = `${this.apiEndpoint}guest/add-list-via-upload`;
      const body = JSON.stringify(guests);
      const httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
      return this.http.post<GuestDTO[]>(url, body, {headers: httpHeaders});
    } catch (e) {
      throw e;
    }
  }

}
