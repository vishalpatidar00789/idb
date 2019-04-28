import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import { MATCHES_ROUTE, MatchesComponent } from './';

@NgModule({
    imports: [IdbSharedModule, RouterModule.forRoot([MATCHES_ROUTE])],
    declarations: [MatchesComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MatchesModule {
    static forRoot() {
        return {
            ngModule: MatchesModule
        };
    }
}
