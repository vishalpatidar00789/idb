import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import {
    IDBUserComponent,
    IDBUserDetailComponent,
    IDBUserUpdateComponent,
    IDBUserDeletePopupComponent,
    IDBUserDeleteDialogComponent,
    iDBUserRoute,
    iDBUserPopupRoute
} from './';

const ENTITY_STATES = [...iDBUserRoute, ...iDBUserPopupRoute];

@NgModule({
    imports: [IdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IDBUserComponent,
        IDBUserDetailComponent,
        IDBUserUpdateComponent,
        IDBUserDeleteDialogComponent,
        IDBUserDeletePopupComponent
    ],
    entryComponents: [IDBUserComponent, IDBUserUpdateComponent, IDBUserDeleteDialogComponent, IDBUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbIDBUserModule {}
