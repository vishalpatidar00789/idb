import { Route } from '@angular/router';

import { PhotoManagementComponent } from './';

export const PHOTO_ROUTE: Route = {
    path: '',
    component: PhotoManagementComponent,
    data: {
        authorities: [],
        pageTitle: 'Photo Management'
    }
};
