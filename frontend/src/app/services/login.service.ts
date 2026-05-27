import { Injectable, Injector } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { NeightApiService } from 'src/neight-api.service';
import { Observable, map, catchError, throwError } from 'rxjs';
import { NeightUserDTO } from '../dto/neightUserDTO';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

	public controller_path: string = "auth_login";

	constructor(protected injector: Injector,
			protected neightApi: NeightApiService,
			protected http: HttpClient) {
	}

	public async authUser(username: string): Promise<boolean> {
			let url: string = this.neightApi.getBackendUrl() + this.controller_path;
			const httpParams = new HttpParams().set('username', username.toString());
			const response: any = await this.http.get(url,  { params: httpParams }).toPromise();
			return response;
	}

	public login(): Observable<NeightUserDTO> {
		const url = `${this.neightApi.getBackendUrl()}login/`;
		const httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
		return this.http.get<NeightUserDTO>(url, {headers: httpHeaders});
		// return this.http.get<any>(url, {headers: httpHeaders, withCredentials: true}).pipe(
		// 	map((data: any) => {
		// 		return data;
		// 	}),
		// 	catchError((error) => {
		// 		return throwError(() => new Error('User not found!'));
		// 	})
		// );
	}
}
