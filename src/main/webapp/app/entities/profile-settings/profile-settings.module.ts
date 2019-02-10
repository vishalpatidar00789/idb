import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import {
    ProfileSettingsComponent,
    ProfileSettingsDetailComponent,
    ProfileSettingsUpdateComponent,
    ProfileSettingsDeletePopupComponent,
    ProfileSettingsDeleteDialogComponent,
    profileSettingsRoute,
    profileSettingsPopupRoute
} from './';

const ENTITY_STATES = [...profileSettingsRoute, ...profileSettingsPopupRoute];

@NgModule({
    imports: [IdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProfileSettingsComponent,
        ProfileSettingsDetailComponent,
        ProfileSettingsUpdateComponent,
        ProfileSettingsDeleteDialogComponent,
        ProfileSettingsDeletePopupComponent
    ],
    entryComponents: [
        ProfileSettingsComponent,
        ProfileSettingsUpdateComponent,
        ProfileSettingsDeleteDialogComponent,
        ProfileSettingsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbProfileSettingsModule {}
