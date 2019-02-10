import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfileSettings } from 'app/shared/model/profile-settings.model';

@Component({
    selector: 'jhi-profile-settings-detail',
    templateUrl: './profile-settings-detail.component.html'
})
export class ProfileSettingsDetailComponent implements OnInit {
    profileSettings: IProfileSettings;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ profileSettings }) => {
            this.profileSettings = profileSettings;
        });
    }

    previousState() {
        window.history.back();
    }
}
