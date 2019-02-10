import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProfileSettings } from 'app/shared/model/profile-settings.model';
import { ProfileSettingsService } from './profile-settings.service';
import { ProfileSettingsComponent } from './profile-settings.component';
import { ProfileSettingsDetailComponent } from './profile-settings-detail.component';
import { ProfileSettingsUpdateComponent } from './profile-settings-update.component';
import { ProfileSettingsDeletePopupComponent } from './profile-settings-delete-dialog.component';
import { IProfileSettings } from 'app/shared/model/profile-settings.model';

@Injectable({ providedIn: 'root' })
export class ProfileSettingsResolve implements Resolve<IProfileSettings> {
    constructor(private service: ProfileSettingsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProfileSettings> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProfileSettings>) => response.ok),
                map((profileSettings: HttpResponse<ProfileSettings>) => profileSettings.body)
            );
        }
        return of(new ProfileSettings());
    }
}

export const profileSettingsRoute: Routes = [
    {
        path: '',
        component: ProfileSettingsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ProfileSettings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProfileSettingsDetailComponent,
        resolve: {
            profileSettings: ProfileSettingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProfileSettings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProfileSettingsUpdateComponent,
        resolve: {
            profileSettings: ProfileSettingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProfileSettings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProfileSettingsUpdateComponent,
        resolve: {
            profileSettings: ProfileSettingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProfileSettings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const profileSettingsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProfileSettingsDeletePopupComponent,
        resolve: {
            profileSettings: ProfileSettingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProfileSettings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
