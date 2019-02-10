import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPayments } from 'app/shared/model/payments.model';

type EntityResponseType = HttpResponse<IPayments>;
type EntityArrayResponseType = HttpResponse<IPayments[]>;

@Injectable({ providedIn: 'root' })
export class PaymentsService {
    public resourceUrl = SERVER_API_URL + 'api/payments';

    constructor(protected http: HttpClient) {}

    create(payments: IPayments): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(payments);
        return this.http
            .post<IPayments>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(payments: IPayments): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(payments);
        return this.http
            .put<IPayments>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPayments>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPayments[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(payments: IPayments): IPayments {
        const copy: IPayments = Object.assign({}, payments, {
            initiatedDate:
                payments.initiatedDate != null && payments.initiatedDate.isValid() ? payments.initiatedDate.format(DATE_FORMAT) : null,
            confirmDate: payments.confirmDate != null && payments.confirmDate.isValid() ? payments.confirmDate.format(DATE_FORMAT) : null,
            createdDate: payments.createdDate != null && payments.createdDate.isValid() ? payments.createdDate.format(DATE_FORMAT) : null,
            lastUpdatedDate:
                payments.lastUpdatedDate != null && payments.lastUpdatedDate.isValid() ? payments.lastUpdatedDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.initiatedDate = res.body.initiatedDate != null ? moment(res.body.initiatedDate) : null;
            res.body.confirmDate = res.body.confirmDate != null ? moment(res.body.confirmDate) : null;
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.lastUpdatedDate = res.body.lastUpdatedDate != null ? moment(res.body.lastUpdatedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((payments: IPayments) => {
                payments.initiatedDate = payments.initiatedDate != null ? moment(payments.initiatedDate) : null;
                payments.confirmDate = payments.confirmDate != null ? moment(payments.confirmDate) : null;
                payments.createdDate = payments.createdDate != null ? moment(payments.createdDate) : null;
                payments.lastUpdatedDate = payments.lastUpdatedDate != null ? moment(payments.lastUpdatedDate) : null;
            });
        }
        return res;
    }
}
