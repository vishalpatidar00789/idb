import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';
import { IIDBUser } from 'app/shared/model/idb-user.model';
import { IDBUserService } from 'app/entities/idb-user';

@Component({
    selector: 'jhi-user-profile-update',
    templateUrl: './user-profile-update.component.html'
})
export class UserProfileUpdateComponent implements OnInit {
    userProfile: IUserProfile;
    isSaving: boolean;

    users: IIDBUser[];
    createdDateDp: any;
    lastUpdatedDateDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected userProfileService: UserProfileService,
        protected iDBUserService: IDBUserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userProfile }) => {
            this.userProfile = userProfile;
        });
        this.iDBUserService
            .query({ filter: 'userprofile-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IIDBUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIDBUser[]>) => response.body)
            )
            .subscribe(
                (res: IIDBUser[]) => {
                    if (!this.userProfile.userId) {
                        this.users = res;
                    } else {
                        this.iDBUserService
                            .find(this.userProfile.userId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IIDBUser>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IIDBUser>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IIDBUser) => (this.users = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userProfile.id !== undefined) {
            this.subscribeToSaveResponse(this.userProfileService.update(this.userProfile));
        } else {
            this.subscribeToSaveResponse(this.userProfileService.create(this.userProfile));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserProfile>>) {
        result.subscribe((res: HttpResponse<IUserProfile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackIDBUserById(index: number, item: IIDBUser) {
        return item.id;
    }
}
