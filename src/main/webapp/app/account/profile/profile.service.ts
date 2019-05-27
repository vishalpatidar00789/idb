import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class ProfileService {
    constructor(private http: HttpClient) {}

    save(account: any): Observable<any> {
        console.log('inside service');
        return this.http.post(SERVER_API_URL + 'api/register', account);
    }
}
