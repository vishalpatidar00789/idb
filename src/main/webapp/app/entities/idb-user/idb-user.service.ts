import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIDBUser } from 'app/shared/model/idb-user.model';

type EntityResponseType = HttpResponse<IIDBUser>;
type EntityArrayResponseType = HttpResponse<IIDBUser[]>;

@Injectable({ providedIn: 'root' })
export class IDBUserService {
    public resourceUrl = SERVER_API_URL + 'api/idb-users';

    constructor(protected http: HttpClient) {}

    create(iDBUser: IIDBUser): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(iDBUser);
        return this.http
            .post<IIDBUser>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(iDBUser: IIDBUser): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(iDBUser);
        return this.http
            .put<IIDBUser>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IIDBUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IIDBUser[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(iDBUser: IIDBUser): IIDBUser {
        const copy: IIDBUser = Object.assign({}, iDBUser, {
            lastLoginDate:
                iDBUser.lastLoginDate != null && iDBUser.lastLoginDate.isValid() ? iDBUser.lastLoginDate.format(DATE_FORMAT) : null,
            lastDeactivatedDate:
                iDBUser.lastDeactivatedDate != null && iDBUser.lastDeactivatedDate.isValid()
                    ? iDBUser.lastDeactivatedDate.format(DATE_FORMAT)
                    : null,
            lastLogout: iDBUser.lastLogout != null && iDBUser.lastLogout.isValid() ? iDBUser.lastLogout.format(DATE_FORMAT) : null,
            lastActivatedDate:
                iDBUser.lastActivatedDate != null && iDBUser.lastActivatedDate.isValid()
                    ? iDBUser.lastActivatedDate.format(DATE_FORMAT)
                    : null,
            createdDate: iDBUser.createdDate != null && iDBUser.createdDate.isValid() ? iDBUser.createdDate.format(DATE_FORMAT) : null,
            lastUpdatedDate:
                iDBUser.lastUpdatedDate != null && iDBUser.lastUpdatedDate.isValid() ? iDBUser.lastUpdatedDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.lastLoginDate = res.body.lastLoginDate != null ? moment(res.body.lastLoginDate) : null;
            res.body.lastDeactivatedDate = res.body.lastDeactivatedDate != null ? moment(res.body.lastDeactivatedDate) : null;
            res.body.lastLogout = res.body.lastLogout != null ? moment(res.body.lastLogout) : null;
            res.body.lastActivatedDate = res.body.lastActivatedDate != null ? moment(res.body.lastActivatedDate) : null;
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.lastUpdatedDate = res.body.lastUpdatedDate != null ? moment(res.body.lastUpdatedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((iDBUser: IIDBUser) => {
                iDBUser.lastLoginDate = iDBUser.lastLoginDate != null ? moment(iDBUser.lastLoginDate) : null;
                iDBUser.lastDeactivatedDate = iDBUser.lastDeactivatedDate != null ? moment(iDBUser.lastDeactivatedDate) : null;
                iDBUser.lastLogout = iDBUser.lastLogout != null ? moment(iDBUser.lastLogout) : null;
                iDBUser.lastActivatedDate = iDBUser.lastActivatedDate != null ? moment(iDBUser.lastActivatedDate) : null;
                iDBUser.createdDate = iDBUser.createdDate != null ? moment(iDBUser.createdDate) : null;
                iDBUser.lastUpdatedDate = iDBUser.lastUpdatedDate != null ? moment(iDBUser.lastUpdatedDate) : null;
            });
        }
        return res;
    }
}
