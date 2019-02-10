import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProfileSettings } from 'app/shared/model/profile-settings.model';
import { ProfileSettingsService } from './profile-settings.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile';

@Component({
    selector: 'jhi-profile-settings-update',
    templateUrl: './profile-settings-update.component.html'
})
export class ProfileSettingsUpdateComponent implements OnInit {
    profileSettings: IProfileSettings;
    isSaving: boolean;

    userprofiles: IUserProfile[];
    createdDateDp: any;
    lastUpdatedDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected profileSettingsService: ProfileSettingsService,
        protected userProfileService: UserProfileService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ profileSettings }) => {
            this.profileSettings = profileSettings;
        });
        this.userProfileService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUserProfile[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserProfile[]>) => response.body)
            )
            .subscribe((res: IUserProfile[]) => (this.userprofiles = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.profileSettings.id !== undefined) {
            this.subscribeToSaveResponse(this.profileSettingsService.update(this.profileSettings));
        } else {
            this.subscribeToSaveResponse(this.profileSettingsService.create(this.profileSettings));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfileSettings>>) {
        result.subscribe((res: HttpResponse<IProfileSettings>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserProfileById(index: number, item: IUserProfile) {
        return item.id;
    }
}
