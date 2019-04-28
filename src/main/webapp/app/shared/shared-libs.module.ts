import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CookieModule } from 'ngx-cookie';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
    imports: [NgbModule.forRoot(), InfiniteScrollModule, CookieModule.forRoot(), FontAwesomeModule],
    exports: [FormsModule, ReactiveFormsModule, CommonModule, NgbModule, NgJhipsterModule, InfiniteScrollModule, FontAwesomeModule]
})
export class IdbSharedLibsModule {
    static forRoot() {
        return {
            ngModule: IdbSharedLibsModule
        };
    }
}
