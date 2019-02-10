import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProfileSettings } from 'app/shared/model/profile-settings.model';

type EntityResponseType = HttpResponse<IProfileSettings>;
type EntityArrayResponseType = HttpResponse<IProfileSettings[]>;

@Injectable({ providedIn: 'root' })
export class ProfileSettingsService {
    public resourceUrl = SERVER_API_URL + 'api/profile-settings';

    constructor(protected http: HttpClient) {}

    create(profileSettings: IProfileSettings): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(profileSettings);
        return this.http
            .post<IProfileSettings>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(profileSettings: IProfileSettings): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(profileSettings);
        return this.http
            .put<IProfileSettings>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProfileSettings>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProfileSettings[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(profileSettings: IProfileSettings): IProfileSettings {
        const copy: IProfileSettings = Object.assign({}, profileSettings, {
            createdDate:
                profileSettings.createdDate != null && profileSettings.createdDate.isValid()
                    ? profileSettings.createdDate.format(DATE_FORMAT)
                    : null,
            lastUpdatedDate:
                profileSettings.lastUpdatedDate != null && profileSettings.lastUpdatedDate.isValid()
                    ? profileSettings.lastUpdatedDate.format(DATE_FORMAT)
                    : null
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
            res.body.forEach((profileSettings: IProfileSettings) => {
                profileSettings.createdDate = profileSettings.createdDate != null ? moment(profileSettings.createdDate) : null;
                profileSettings.lastUpdatedDate = profileSettings.lastUpdatedDate != null ? moment(profileSettings.lastUpdatedDate) : null;
            });
        }
        return res;
    }
}
