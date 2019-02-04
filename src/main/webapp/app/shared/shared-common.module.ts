import { NgModule } from '@angular/core';

import { IdbSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [IdbSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [IdbSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class IdbSharedCommonModule {}
