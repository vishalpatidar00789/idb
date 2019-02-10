import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import {
    PaymentsComponent,
    PaymentsDetailComponent,
    PaymentsUpdateComponent,
    PaymentsDeletePopupComponent,
    PaymentsDeleteDialogComponent,
    paymentsRoute,
    paymentsPopupRoute
} from './';

const ENTITY_STATES = [...paymentsRoute, ...paymentsPopupRoute];

@NgModule({
    imports: [IdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PaymentsComponent,
        PaymentsDetailComponent,
        PaymentsUpdateComponent,
        PaymentsDeleteDialogComponent,
        PaymentsDeletePopupComponent
    ],
    entryComponents: [PaymentsComponent, PaymentsUpdateComponent, PaymentsDeleteDialogComponent, PaymentsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbPaymentsModule {}
