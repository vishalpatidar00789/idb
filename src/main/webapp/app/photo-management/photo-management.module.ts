import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import { PHOTO_ROUTE, PhotoManagementComponent, PhotoPanelComponent } from './';

@NgModule({
    imports: [IdbSharedModule, RouterModule.forChild([PHOTO_ROUTE])],
    declarations: [PhotoManagementComponent, PhotoPanelComponent],
    exports: [PhotoManagementComponent, PhotoPanelComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PhotoManagementModule {}
