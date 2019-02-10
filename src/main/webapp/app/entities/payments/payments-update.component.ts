import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPayments } from 'app/shared/model/payments.model';
import { PaymentsService } from './payments.service';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from 'app/entities/user-account';
import { IPackages } from 'app/shared/model/packages.model';
import { PackagesService } from 'app/entities/packages';

@Component({
    selector: 'jhi-payments-update',
    templateUrl: './payments-update.component.html'
})
export class PaymentsUpdateComponent implements OnInit {
    payments: IPayments;
    isSaving: boolean;

    useraccounts: IUserAccount[];

    packages: IPackages[];
    initiatedDateDp: any;
    confirmDateDp: any;
    createdDateDp: any;
    lastUpdatedDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected paymentsService: PaymentsService,
        protected userAccountService: UserAccountService,
        protected packagesService: PackagesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ payments }) => {
            this.payments = payments;
        });
        this.userAccountService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUserAccount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserAccount[]>) => response.body)
            )
            .subscribe((res: IUserAccount[]) => (this.useraccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.packagesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPackages[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPackages[]>) => response.body)
            )
            .subscribe((res: IPackages[]) => (this.packages = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.payments.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentsService.update(this.payments));
        } else {
            this.subscribeToSaveResponse(this.paymentsService.create(this.payments));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayments>>) {
        result.subscribe((res: HttpResponse<IPayments>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserAccountById(index: number, item: IUserAccount) {
        return item.id;
    }

    trackPackagesById(index: number, item: IPackages) {
        return item.id;
    }
}
