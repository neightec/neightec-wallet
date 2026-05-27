import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { NeightApiService } from 'src/neight-api.service';
import { NeightUserDTO } from '../dto/neightUserDTO';
import { ACCESS_TOKEN } from '../models/auth.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authenticated: any;
  currentUser: NeightUserDTO;

  constructor(
    private http: HttpClient,
    private route: Router,
    protected neightApi: NeightApiService) {}

  login(username: string, password: string): Observable<any> {
    const body = new HttpParams()
    .set('username', username)
    .set('password', password);

    let url: string = this.neightApi.getBackendUrl() + "token";
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    console.warn("body: ", body, body.toString());
    return this.http.post(url, body.toString(), {headers: headers, observe: "response"});
  }

  getAccessToken(): string | null {
    return localStorage.getItem('access_token');
  }

  createHeaders(): HttpHeaders {
    const accessToken = this.getAccessToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${accessToken}`
    });
  }

  //example protected route
  accessProtectedRoute(): Observable<any> {
    const headers = this.createHeaders();
    return this.http.get(`${this.neightApi.getBackendUrl()}protected`, { headers });
  }

  setAuthentication(accessToken: string) {
    if (accessToken) {
      localStorage.setItem(ACCESS_TOKEN, accessToken);
      this.authenticated = true;
    }
  }

  logout() {
    localStorage.removeItem(ACCESS_TOKEN);
    this.authenticated = false;
    this.currentUser = null;
    this.route.navigate(['/login']);
  }
}
