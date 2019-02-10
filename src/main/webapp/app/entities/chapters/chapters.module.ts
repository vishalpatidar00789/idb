import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import {
    ChaptersComponent,
    ChaptersDetailComponent,
    ChaptersUpdateComponent,
    ChaptersDeletePopupComponent,
    ChaptersDeleteDialogComponent,
    chaptersRoute,
    chaptersPopupRoute
} from './';

const ENTITY_STATES = [...chaptersRoute, ...chaptersPopupRoute];

@NgModule({
    imports: [IdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChaptersComponent,
        ChaptersDetailComponent,
        ChaptersUpdateComponent,
        ChaptersDeleteDialogComponent,
        ChaptersDeletePopupComponent
    ],
    entryComponents: [ChaptersComponent, ChaptersUpdateComponent, ChaptersDeleteDialogComponent, ChaptersDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbChaptersModule {}
