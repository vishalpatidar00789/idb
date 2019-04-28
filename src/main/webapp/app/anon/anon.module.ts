import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import { ANNON_ROUTE } from 'app/anon/anon.route';
import { AnonComponent } from 'app/anon/anon.component';

@NgModule({
    imports: [IdbSharedModule, RouterModule.forChild([ANNON_ROUTE])],
    declarations: [AnonComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [AnonComponent]
})
export class AnonModule {}
