import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChapters } from 'app/shared/model/chapters.model';

type EntityResponseType = HttpResponse<IChapters>;
type EntityArrayResponseType = HttpResponse<IChapters[]>;

@Injectable({ providedIn: 'root' })
export class ChaptersService {
    public resourceUrl = SERVER_API_URL + 'api/chapters';

    constructor(protected http: HttpClient) {}

    create(chapters: IChapters): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(chapters);
        return this.http
            .post<IChapters>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(chapters: IChapters): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(chapters);
        return this.http
            .put<IChapters>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IChapters>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IChapters[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(chapters: IChapters): IChapters {
        const copy: IChapters = Object.assign({}, chapters, {
            createdDate: chapters.createdDate != null && chapters.createdDate.isValid() ? chapters.createdDate.format(DATE_FORMAT) : null,
            lastUpdatedDate:
                chapters.lastUpdatedDate != null && chapters.lastUpdatedDate.isValid() ? chapters.lastUpdatedDate.format(DATE_FORMAT) : null
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
            res.body.forEach((chapters: IChapters) => {
                chapters.createdDate = chapters.createdDate != null ? moment(chapters.createdDate) : null;
                chapters.lastUpdatedDate = chapters.lastUpdatedDate != null ? moment(chapters.lastUpdatedDate) : null;
            });
        }
        return res;
    }
}
