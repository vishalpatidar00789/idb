import { Route, Routes } from '@angular/router';

import { MatchesComponent } from './matches.component';

export const MATCHES_ROUTE: Route = {
    path: 'matches',
    component: MatchesComponent,
    data: {
        authorities: [],
        pageTitle: 'Welcome, Java Hipster!'
    }
};
