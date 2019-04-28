import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPackages } from 'app/shared/model/packages.model';

type EntityArrayResponseType = HttpResponse<IPackages[]>;

@Injectable({ providedIn: 'root' })
export class UserPackagesService {
    public resourceUrl = SERVER_API_URL + 'api/packages';

    constructor(protected http: HttpClient) {}

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPackages[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
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
