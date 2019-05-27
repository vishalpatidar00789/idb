import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { IdbSharedLibsModule, IdbSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { JhMaterialModule } from 'app/app-material.module';
import { NgxGalleryModule } from 'ngx-gallery';
import { FiltersComponent } from 'app/shared/filters/filters.component';
import { BannerComponent } from 'app/shared/banner/banner.component';
import { CarouselModule } from 'ngx-bootstrap';
import { MatchHolderComponent } from './match-holder/match-holder.component';
import { SignupComponent } from './signup/signup.component';
import { ConfirmDialogComponent } from 'app/core/confirm-dialog/confirm-dialog.component';

@NgModule({
    imports: [IdbSharedLibsModule, IdbSharedCommonModule, JhMaterialModule, NgxGalleryModule, CarouselModule.forRoot()],
    declarations: [
        SignupComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        FiltersComponent,
        BannerComponent,
        MatchHolderComponent,
        ConfirmDialogComponent
    ],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent, ConfirmDialogComponent],
    exports: [
        IdbSharedCommonModule,
        SignupComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        JhMaterialModule,
        NgxGalleryModule,
        CarouselModule,
        FiltersComponent,
        BannerComponent,
        MatchHolderComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbSharedModule {
    static forRoot() {
        return {
            ngModule: IdbSharedModule
        };
    }
}
