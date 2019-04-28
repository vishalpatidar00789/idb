import { Route } from '@angular/router';

import { PaymentComponent } from './payment.component';

export const paymentRoute: Route = {
    path: 'payment',
    component: PaymentComponent,
    data: {
        authorities: [],
        pageTitle: 'Payment'
    }
};
