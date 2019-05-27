import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IPhotos } from 'app/shared/model/photos.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

@Injectable({ providedIn: 'root' })
export class PhotoService {
    public resourceUrl = SERVER_API_URL + 'api/photos';
    constructor(private http: HttpClient) {}

    create(user: IPhotos): Observable<HttpResponse<IPhotos>> {
        return this.http.post<IPhotos>(this.resourceUrl, user, { observe: 'response' });
    }

    update(user: IPhotos): Observable<HttpResponse<IPhotos>> {
        return this.http.put<IPhotos>(this.resourceUrl, user, { observe: 'response' });
    }

    find(login: string): Observable<HttpResponse<IPhotos>> {
        return this.http.get<IPhotos>(`${this.resourceUrl}/${login}`, { observe: 'response' });
    }

    query(req?: any): Observable<HttpResponse<IPhotos[]>> {
        const options = createRequestOption(req);
        return this.http.get<IPhotos[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(login: string): Observable<HttpResponse<any>> {
        return this.http.delete(`${this.resourceUrl}/${login}`, { observe: 'response' });
    }

    queryPhotoList(req?: any): Observable<IPhotos[]> {
        const photoList: IPhotos[] = [];
        for (let i = 1; i <= 10; i++) {
            photoList.push({
                id: i,
                imageContentType: 'image/jpg',
                image: `../../content/images/profile-image/profile_${i}.jpg`,
                isProfilePicture: i === 1 ? true : false,
                orderInProfile: i,
                createdDate: moment(),
                createdBy: 'Admin',
                userProfileId: 123
            });
        }
        return of(photoList);
    }
}
