import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';
import { MATCHES_ROUTE } from './matches.route';
import { ListMatchesComponent } from './list-matches.component';

@NgModule({
  imports: [IdbSharedModule, RouterModule.forChild([MATCHES_ROUTE])],
    declarations: [ListMatchesComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MatchesModule { }
