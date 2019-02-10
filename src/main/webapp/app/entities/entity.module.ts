import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'idb-user',
                loadChildren: './idb-user/idb-user.module#IdbIDBUserModule'
            },
            {
                path: 'user-profile',
                loadChildren: './user-profile/user-profile.module#IdbUserProfileModule'
            },
            {
                path: 'profile-settings',
                loadChildren: './profile-settings/profile-settings.module#IdbProfileSettingsModule'
            },
            {
                path: 'photos',
                loadChildren: './photos/photos.module#IdbPhotosModule'
            },
            {
                path: 'user-account',
                loadChildren: './user-account/user-account.module#IdbUserAccountModule'
            },
            {
                path: 'packages',
                loadChildren: './packages/packages.module#IdbPackagesModule'
            },
            {
                path: 'chapters',
                loadChildren: './chapters/chapters.module#IdbChaptersModule'
            },
            {
                path: 'payments',
                loadChildren: './payments/payments.module#IdbPaymentsModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbEntityModule {}
