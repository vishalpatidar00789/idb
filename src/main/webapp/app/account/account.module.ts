import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IdbSharedModule } from 'app/shared';

import {
    PasswordStrengthBarComponent,
    RegisterComponent,
    ActivateComponent,
    PasswordComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
    PaymentComponent,
    PackagesComponent,
    ProfileComponent,
    ProfileFormComponent,
    accountState
} from './';
import { ReactiveFormsModule } from '@angular/forms';
import { PrefsComponent } from './prefs/prefs.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PhotoManagementModule } from '../photo-management';

@NgModule({
    imports: [IdbSharedModule, RouterModule.forChild(accountState), ReactiveFormsModule, PhotoManagementModule],
    declarations: [
        ActivateComponent,
        RegisterComponent,
        PasswordComponent,
        PasswordStrengthBarComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SettingsComponent,
        PaymentComponent,
        PackagesComponent,
        PrefsComponent,
        DashboardComponent,
        ProfileComponent,
        ProfileFormComponent
    ],
    entryComponents: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IdbAccountModule {}
