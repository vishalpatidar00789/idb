import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { DashboardComponent } from './dashboard.component';

export const dashboardRoute: Route = {
    path: 'dashboard',
    component: DashboardComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Dashboard'
    },
    canActivate: [UserRouteAccessService]
};
