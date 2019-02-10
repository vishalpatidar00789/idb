import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserAccount } from 'app/shared/model/user-account.model';

type EntityResponseType = HttpResponse<IUserAccount>;
type EntityArrayResponseType = HttpResponse<IUserAccount[]>;

@Injectable({ providedIn: 'root' })
export class UserAccountService {
    public resourceUrl = SERVER_API_URL + 'api/user-accounts';

    constructor(protected http: HttpClient) {}

    create(userAccount: IUserAccount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userAccount);
        return this.http
            .post<IUserAccount>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(userAccount: IUserAccount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userAccount);
        return this.http
            .put<IUserAccount>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IUserAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(userAccount: IUserAccount): IUserAccount {
        const copy: IUserAccount = Object.assign({}, userAccount, {
            currPackageStartDate:
                userAccount.currPackageStartDate != null && userAccount.currPackageStartDate.isValid()
                    ? userAccount.currPackageStartDate.format(DATE_FORMAT)
                    : null,
            currPackageEndDate:
                userAccount.currPackageEndDate != null && userAccount.currPackageEndDate.isValid()
                    ? userAccount.currPackageEndDate.format(DATE_FORMAT)
                    : null,
            createdDate:
                userAccount.createdDate != null && userAccount.createdDate.isValid() ? userAccount.createdDate.format(DATE_FORMAT) : null,
            lastUpdatedDate:
                userAccount.lastUpdatedDate != null && userAccount.lastUpdatedDate.isValid()
                    ? userAccount.lastUpdatedDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.currPackageStartDate = res.body.currPackageStartDate != null ? moment(res.body.currPackageStartDate) : null;
            res.body.currPackageEndDate = res.body.currPackageEndDate != null ? moment(res.body.currPackageEndDate) : null;
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.lastUpdatedDate = res.body.lastUpdatedDate != null ? moment(res.body.lastUpdatedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((userAccount: IUserAccount) => {
                userAccount.currPackageStartDate =
                    userAccount.currPackageStartDate != null ? moment(userAccount.currPackageStartDate) : null;
                userAccount.currPackageEndDate = userAccount.currPackageEndDate != null ? moment(userAccount.currPackageEndDate) : null;
                userAccount.createdDate = userAccount.createdDate != null ? moment(userAccount.createdDate) : null;
                userAccount.lastUpdatedDate = userAccount.lastUpdatedDate != null ? moment(userAccount.lastUpdatedDate) : null;
            });
        }
        return res;
    }
}
