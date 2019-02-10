import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import {
    PhotosComponent,
    PhotosDetailComponent,
    PhotosUpdateComponent,
    PhotosDeletePopupComponent,
    PhotosDeleteDialogComponent,
    photosRoute,
    photosPopupRoute
} from './';

const ENTITY_STATES = [...photosRoute, ...photosPopupRoute];

@NgModule({
    imports: [IdbSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PhotosComponent, PhotosDetailComponent, PhotosUpdateComponent, PhotosDeleteDialogComponent, PhotosDeletePopupComponent],
    entryComponents: [PhotosComponent, PhotosUpdateComponent, PhotosDeleteDialogComponent, PhotosDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbPhotosModule {}
