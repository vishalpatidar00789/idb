import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { SettingsComponent } from './settings.component';
import { MATCHES_ROUTE } from 'app/matches';

export const settingsRoute: Route = {
    path: 'settings',
    component: SettingsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Settings'
    },
    canActivate: [UserRouteAccessService],
    children: [MATCHES_ROUTE]
};
