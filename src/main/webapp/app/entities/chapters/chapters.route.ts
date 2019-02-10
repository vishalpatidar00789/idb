import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Chapters } from 'app/shared/model/chapters.model';
import { ChaptersService } from './chapters.service';
import { ChaptersComponent } from './chapters.component';
import { ChaptersDetailComponent } from './chapters-detail.component';
import { ChaptersUpdateComponent } from './chapters-update.component';
import { ChaptersDeletePopupComponent } from './chapters-delete-dialog.component';
import { IChapters } from 'app/shared/model/chapters.model';

@Injectable({ providedIn: 'root' })
export class ChaptersResolve implements Resolve<IChapters> {
    constructor(private service: ChaptersService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChapters> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Chapters>) => response.ok),
                map((chapters: HttpResponse<Chapters>) => chapters.body)
            );
        }
        return of(new Chapters());
    }
}

export const chaptersRoute: Routes = [
    {
        path: '',
        component: ChaptersComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Chapters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ChaptersDetailComponent,
        resolve: {
            chapters: ChaptersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chapters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ChaptersUpdateComponent,
        resolve: {
            chapters: ChaptersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chapters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ChaptersUpdateComponent,
        resolve: {
            chapters: ChaptersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chapters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chaptersPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ChaptersDeletePopupComponent,
        resolve: {
            chapters: ChaptersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chapters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
