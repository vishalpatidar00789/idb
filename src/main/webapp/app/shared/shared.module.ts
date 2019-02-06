import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { IdbSharedLibsModule, IdbSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { JhMaterialModule } from 'app/app-material.module';

@NgModule({
    imports: [IdbSharedLibsModule, IdbSharedCommonModule, JhMaterialModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [IdbSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, JhMaterialModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbSharedModule {
    static forRoot() {
        return {
            ngModule: IdbSharedModule
        };
    }
}
