import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Payments } from 'app/shared/model/payments.model';
import { PaymentsService } from './payments.service';
import { PaymentsComponent } from './payments.component';
import { PaymentsDetailComponent } from './payments-detail.component';
import { PaymentsUpdateComponent } from './payments-update.component';
import { PaymentsDeletePopupComponent } from './payments-delete-dialog.component';
import { IPayments } from 'app/shared/model/payments.model';

@Injectable({ providedIn: 'root' })
export class PaymentsResolve implements Resolve<IPayments> {
    constructor(private service: PaymentsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPayments> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Payments>) => response.ok),
                map((payments: HttpResponse<Payments>) => payments.body)
            );
        }
        return of(new Payments());
    }
}

export const paymentsRoute: Routes = [
    {
        path: '',
        component: PaymentsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Payments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PaymentsDetailComponent,
        resolve: {
            payments: PaymentsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Payments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PaymentsUpdateComponent,
        resolve: {
            payments: PaymentsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Payments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PaymentsUpdateComponent,
        resolve: {
            payments: PaymentsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Payments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PaymentsDeletePopupComponent,
        resolve: {
            payments: PaymentsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Payments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
