import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import {
    PackagesComponent,
    PackagesDetailComponent,
    PackagesUpdateComponent,
    PackagesDeletePopupComponent,
    PackagesDeleteDialogComponent,
    packagesRoute,
    packagesPopupRoute
} from './';

const ENTITY_STATES = [...packagesRoute, ...packagesPopupRoute];

@NgModule({
    imports: [IdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PackagesComponent,
        PackagesDetailComponent,
        PackagesUpdateComponent,
        PackagesDeleteDialogComponent,
        PackagesDeletePopupComponent
    ],
    entryComponents: [PackagesComponent, PackagesUpdateComponent, PackagesDeleteDialogComponent, PackagesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbPackagesModule {}
