import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import { AnonComponent } from 'app/anon/anon.component';
import { anonStates, ANNON_PUBLIC_ROUTES } from './anon.route';
import { AnnonDashboardComponent } from './annon-dashboard/annon-dashboard.component';

@NgModule({
    imports: [IdbSharedModule, RouterModule.forRoot(ANNON_PUBLIC_ROUTES)],
    declarations: [AnonComponent, AnnonDashboardComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [AnonComponent, AnnonDashboardComponent]
})
export class AnonModule {}
