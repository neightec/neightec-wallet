import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { NeightApiService } from 'src/neight-api.service';

@Injectable({
  providedIn: 'root'
})
export class QrCodeWeddingService {

  public controller_path: string = "generate_qr_codes";

  constructor(protected injector: Injector,
      protected neightApi: NeightApiService,
      protected http: HttpClient) {
  }

  public async generateQrCodes(): Promise<any> {
    let url: string = this.neightApi.getBackendUrl() + this.controller_path;
    const httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
    const response: any = await this.http.get(url, {headers: httpHeaders}).toPromise();
    return response;
  }
}
