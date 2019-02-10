import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IChapters } from 'app/shared/model/chapters.model';
import { ChaptersService } from './chapters.service';
import { IIDBUser } from 'app/shared/model/idb-user.model';
import { IDBUserService } from 'app/entities/idb-user';

@Component({
    selector: 'jhi-chapters-update',
    templateUrl: './chapters-update.component.html'
})
export class ChaptersUpdateComponent implements OnInit {
    chapters: IChapters;
    isSaving: boolean;

    idbusers: IIDBUser[];
    createdDateDp: any;
    lastUpdatedDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected chaptersService: ChaptersService,
        protected iDBUserService: IDBUserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chapters }) => {
            this.chapters = chapters;
        });
        this.iDBUserService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIDBUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIDBUser[]>) => response.body)
            )
            .subscribe((res: IIDBUser[]) => (this.idbusers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.chapters.id !== undefined) {
            this.subscribeToSaveResponse(this.chaptersService.update(this.chapters));
        } else {
            this.subscribeToSaveResponse(this.chaptersService.create(this.chapters));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IChapters>>) {
        result.subscribe((res: HttpResponse<IChapters>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackIDBUserById(index: number, item: IIDBUser) {
        return item.id;
    }
}
