import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPhotos } from 'app/shared/model/photos.model';
import { PhotosService } from './photos.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile';

@Component({
    selector: 'jhi-photos-update',
    templateUrl: './photos-update.component.html'
})
export class PhotosUpdateComponent implements OnInit {
    photos: IPhotos;
    isSaving: boolean;

    userprofiles: IUserProfile[];
    createdDateDp: any;
    lastUpdatedDateDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected photosService: PhotosService,
        protected userProfileService: UserProfileService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ photos }) => {
            this.photos = photos;
        });
        this.userProfileService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUserProfile[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserProfile[]>) => response.body)
            )
            .subscribe((res: IUserProfile[]) => (this.userprofiles = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.photos.id !== undefined) {
            this.subscribeToSaveResponse(this.photosService.update(this.photos));
        } else {
            this.subscribeToSaveResponse(this.photosService.create(this.photos));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhotos>>) {
        result.subscribe((res: HttpResponse<IPhotos>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
