import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IIDBUser } from 'app/shared/model/idb-user.model';
import { IDBUserService } from './idb-user.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from 'app/entities/user-account';

@Component({
    selector: 'jhi-idb-user-update',
    templateUrl: './idb-user-update.component.html'
})
export class IDBUserUpdateComponent implements OnInit {
    iDBUser: IIDBUser;
    isSaving: boolean;

    userprofiles: IUserProfile[];

    useraccounts: IUserAccount[];
    lastLoginDateDp: any;
    lastDeactivatedDateDp: any;
    lastLogoutDp: any;
    lastActivatedDateDp: any;
    createdDateDp: any;
    lastUpdatedDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected iDBUserService: IDBUserService,
        protected userProfileService: UserProfileService,
        protected userAccountService: UserAccountService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ iDBUser }) => {
            this.iDBUser = iDBUser;
        });
        this.userProfileService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUserProfile[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserProfile[]>) => response.body)
            )
            .subscribe((res: IUserProfile[]) => (this.userprofiles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.userAccountService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUserAccount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserAccount[]>) => response.body)
            )
            .subscribe((res: IUserAccount[]) => (this.useraccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.iDBUser.id !== undefined) {
            this.subscribeToSaveResponse(this.iDBUserService.update(this.iDBUser));
        } else {
            this.subscribeToSaveResponse(this.iDBUserService.create(this.iDBUser));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIDBUser>>) {
        result.subscribe((res: HttpResponse<IIDBUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserProfileById(index: number, item: IUserProfile) {
        return item.id;
    }

    trackUserAccountById(index: number, item: IUserAccount) {
        return item.id;
    }
}
