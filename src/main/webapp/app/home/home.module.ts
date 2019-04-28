import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { AnonModule } from 'app/anon';

@NgModule({
    imports: [IdbSharedModule, RouterModule.forChild([HOME_ROUTE]), AnonModule],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbHomeModule {}
