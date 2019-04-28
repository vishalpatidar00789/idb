import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { PrefsComponent } from './prefs.component';

export const preferenceRoute: Route = {
    path: 'preference',
    component: PrefsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Preferences'
    },
    canActivate: [UserRouteAccessService]
};
