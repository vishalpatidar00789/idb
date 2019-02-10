import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from './user-account.service';
import { IIDBUser } from 'app/shared/model/idb-user.model';
import { IDBUserService } from 'app/entities/idb-user';
import { IPackages } from 'app/shared/model/packages.model';
import { PackagesService } from 'app/entities/packages';

@Component({
    selector: 'jhi-user-account-update',
    templateUrl: './user-account-update.component.html'
})
export class UserAccountUpdateComponent implements OnInit {
    userAccount: IUserAccount;
    isSaving: boolean;

    users: IIDBUser[];

    currentpackages: IPackages[];
    currPackageStartDateDp: any;
    currPackageEndDateDp: any;
    createdDateDp: any;
    lastUpdatedDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected userAccountService: UserAccountService,
        protected iDBUserService: IDBUserService,
        protected packagesService: PackagesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userAccount }) => {
            this.userAccount = userAccount;
        });
        this.iDBUserService
            .query({ filter: 'useraccount-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IIDBUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIDBUser[]>) => response.body)
            )
            .subscribe(
                (res: IIDBUser[]) => {
                    if (!this.userAccount.userId) {
                        this.users = res;
                    } else {
                        this.iDBUserService
                            .find(this.userAccount.userId)
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
        this.packagesService
            .query({ filter: 'useraccount-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPackages[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPackages[]>) => response.body)
            )
            .subscribe(
                (res: IPackages[]) => {
                    if (!this.userAccount.currentPackageId) {
                        this.currentpackages = res;
                    } else {
                        this.packagesService
                            .find(this.userAccount.currentPackageId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPackages>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPackages>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPackages) => (this.currentpackages = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.userAccountService.update(this.userAccount));
        } else {
            this.subscribeToSaveResponse(this.userAccountService.create(this.userAccount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserAccount>>) {
        result.subscribe((res: HttpResponse<IUserAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPackagesById(index: number, item: IPackages) {
        return item.id;
    }
}
