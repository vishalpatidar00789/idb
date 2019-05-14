import { Route } from '@angular/router';
import { AnnonDashboardComponent } from './annon-dashboard.component';
import { AnnonRouteResolverService } from '../annon.route.resolver.service';

export const annonPublicFemalePage: Route = {
    path: 'women-seeking-for-men',
    component: AnnonDashboardComponent,
    data: {
        authorities: [],
        pageTitle: 'Girls looking for boys'
    }
};

export const annonPublicMalePage: Route = {
    path: 'men-seeking-for-women',
    component: AnnonDashboardComponent,
    data: {
        authorities: [],
        pageTitle: 'Boys looking for girls'
    }
};

export const annonPublicFemalePage2: Route = {
    path: 'women-seeking-for-men/:city/*',
    component: AnnonDashboardComponent,
    data: {
        authorities: [],
        pageTitle: 'Women seeking for men'
    },
    resolve: { matches: AnnonRouteResolverService }
};

export const annonPublicMalePage2: Route = {
    path: 'men-seeking-for-women/:city/*',
    component: AnnonDashboardComponent,
    data: {
        authorities: [],
        pageTitle: 'Men seeking for women'
    }
};
