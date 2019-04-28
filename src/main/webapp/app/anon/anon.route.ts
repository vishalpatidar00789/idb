import { Route } from '@angular/router';
import { AnonComponent } from 'app/anon/anon.component';

export const ANNON_ROUTE: Route = {
    path: 'anon-landing-page',
    component: AnonComponent,
    data: {
        authorities: [],
        pageTitle: 'Welcome, User!'
    }
};
