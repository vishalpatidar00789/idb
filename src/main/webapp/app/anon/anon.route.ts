import { Route, Routes } from '@angular/router';
import { AnonComponent } from 'app/anon/anon.component';
import {
    annonPublicFemalePage,
    annonPublicMalePage,
    annonPublicFemalePage2,
    annonPublicMalePage2
} from './annon-dashboard/annon-dashboard.route';

export const annonQueryByMoodRoute: Route = {
    path: 'search-by-mood',
    component: AnonComponent,
    data: {
        authorities: [],
        pageTitle: 'Welcome, User!'
    }
};

export const ANNON_PUBLIC_ROUTES = [
    annonQueryByMoodRoute,
    annonPublicFemalePage,
    annonPublicMalePage,
    annonPublicFemalePage2,
    annonPublicMalePage2
];

export const anonStates: Routes = [
    {
        path: 'search-by-mood',
        component: AnonComponent
        // children: ANNON_PUBLIC_ROUTES
    }
];
