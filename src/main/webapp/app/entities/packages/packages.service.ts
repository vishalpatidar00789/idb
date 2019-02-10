import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPackages } from 'app/shared/model/packages.model';

type EntityResponseType = HttpResponse<IPackages>;
type EntityArrayResponseType = HttpResponse<IPackages[]>;

@Injectable({ providedIn: 'root' })
export class PackagesService {
    public resourceUrl = SERVER_API_URL + 'api/packages';

    constructor(protected http: HttpClient) {}

    create(packages: IPackages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(packages);
        return this.http
            .post<IPackages>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(packages: IPackages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(packages);
        return this.http
            .put<IPackages>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPackages>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPackages[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(packages: IPackages): IPackages {
        const copy: IPackages = Object.assign({}, packages, {
            createdDate: packages.createdDate != null && packages.createdDate.isValid() ? packages.createdDate.format(DATE_FORMAT) : null,
            lastUpdatedDate:
                packages.lastUpdatedDate != null && packages.lastUpdatedDate.isValid() ? packages.lastUpdatedDate.format(DATE_FORMAT) : null
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
            res.body.forEach((packages: IPackages) => {
                packages.createdDate = packages.createdDate != null ? moment(packages.createdDate) : null;
                packages.lastUpdatedDate = packages.lastUpdatedDate != null ? moment(packages.lastUpdatedDate) : null;
            });
        }
        return res;
    }
}
