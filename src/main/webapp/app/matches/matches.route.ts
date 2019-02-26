import { Route } from '@angular/router';

import { ListMatchesComponent } from './list-matches.component';

export const MATCHES_ROUTE: Route = {
    path: 'listMatches',
    component: ListMatchesComponent,
    data: {
        authorities: [],
        pageTitle: 'Welcome, Java Hipster!'
    }
};
