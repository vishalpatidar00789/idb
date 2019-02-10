import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDBUser } from 'app/shared/model/idb-user.model';
import { IDBUserService } from './idb-user.service';
import { IDBUserComponent } from './idb-user.component';
import { IDBUserDetailComponent } from './idb-user-detail.component';
import { IDBUserUpdateComponent } from './idb-user-update.component';
import { IDBUserDeletePopupComponent } from './idb-user-delete-dialog.component';
import { IIDBUser } from 'app/shared/model/idb-user.model';

@Injectable({ providedIn: 'root' })
export class IDBUserResolve implements Resolve<IIDBUser> {
    constructor(private service: IDBUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIDBUser> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IDBUser>) => response.ok),
                map((iDBUser: HttpResponse<IDBUser>) => iDBUser.body)
            );
        }
        return of(new IDBUser());
    }
}

export const iDBUserRoute: Routes = [
    {
        path: '',
        component: IDBUserComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'IDBUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IDBUserDetailComponent,
        resolve: {
            iDBUser: IDBUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IDBUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IDBUserUpdateComponent,
        resolve: {
            iDBUser: IDBUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IDBUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IDBUserUpdateComponent,
        resolve: {
            iDBUser: IDBUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IDBUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const iDBUserPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IDBUserDeletePopupComponent,
        resolve: {
            iDBUser: IDBUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IDBUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
