import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class AnnonService {
    constructor(private http: HttpClient) {}

    fetch(): Observable<HttpResponse<any>> {
        return this.http.get<any>(SERVER_API_URL + 'api/search-by-mood', { observe: 'response' });
    }
}
