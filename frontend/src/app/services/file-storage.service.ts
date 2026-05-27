import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable, Injector} from '@angular/core';
import {catchError, EMPTY, Observable, tap} from 'rxjs';
import {DocumentDTO} from '../dto/documentDTO';
import { neightEnvironment } from 'src/environments/environment';
import { NeightApiService } from 'src/neight-api.service';

@Injectable({
  providedIn: 'root',
})
export class FileStorageService {
  readonly apiEndpoint: string;
  readonly fileStoragePath: string = "filestorage";

  constructor(
    protected injector: Injector,
    protected neightApi: NeightApiService,
    protected http: HttpClient) {
    this.apiEndpoint = `${neightEnvironment.api_url}`;
  }

  getUploadedFiles(campaignId: string): Observable<DocumentDTO[]> {
    return this.http.get<DocumentDTO[]>(`${this.fileStoragePath}/files?campaign=${campaignId}`);
  }

  uploadFile(campaignId: string, file: any): Observable<string> {
    const externalFileExtensions: string = this.getFileExtension(file.name);
    const formData = new FormData();
    formData.append('campaign', campaignId);
    formData.append('file', file);
    formData.append('externalFileExtensions', externalFileExtensions);
    return this.http.post<string>(`${this.fileStoragePath}/file`, formData);
  }

  getFileExtension(filename: string): string {
    const extension: boolean = filename ? filename.includes('.') : false;
    return extension ? filename.slice(filename.lastIndexOf('.') + 1) : "";
  }
}
