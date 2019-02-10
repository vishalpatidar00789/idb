import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPhotos } from 'app/shared/model/photos.model';

type EntityResponseType = HttpResponse<IPhotos>;
type EntityArrayResponseType = HttpResponse<IPhotos[]>;

@Injectable({ providedIn: 'root' })
export class PhotosService {
    public resourceUrl = SERVER_API_URL + 'api/photos';

    constructor(protected http: HttpClient) {}

    create(photos: IPhotos): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(photos);
        return this.http
            .post<IPhotos>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(photos: IPhotos): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(photos);
        return this.http
            .put<IPhotos>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPhotos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPhotos[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(photos: IPhotos): IPhotos {
        const copy: IPhotos = Object.assign({}, photos, {
            createdDate: photos.createdDate != null && photos.createdDate.isValid() ? photos.createdDate.format(DATE_FORMAT) : null,
            lastUpdatedDate:
                photos.lastUpdatedDate != null && photos.lastUpdatedDate.isValid() ? photos.lastUpdatedDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.lastUpdatedDate = res.body.lastUpdatedDate != null ? moment(res.body.lastUpdatedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((photos: IPhotos) => {
                photos.createdDate = photos.createdDate != null ? moment(photos.createdDate) : null;
                photos.lastUpdatedDate = photos.lastUpdatedDate != null ? moment(photos.lastUpdatedDate) : null;
            });
        }
        return res;
    }
}
