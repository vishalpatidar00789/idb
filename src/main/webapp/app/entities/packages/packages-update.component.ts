import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IPackages } from 'app/shared/model/packages.model';
import { PackagesService } from './packages.service';

@Component({
    selector: 'jhi-packages-update',
    templateUrl: './packages-update.component.html'
})
export class PackagesUpdateComponent implements OnInit {
    packages: IPackages;
    isSaving: boolean;
    createdDateDp: any;
    lastUpdatedDateDp: any;

    constructor(protected packagesService: PackagesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ packages }) => {
            this.packages = packages;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.packages.id !== undefined) {
            this.subscribeToSaveResponse(this.packagesService.update(this.packages));
        } else {
            this.subscribeToSaveResponse(this.packagesService.create(this.packages));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPackages>>) {
        result.subscribe((res: HttpResponse<IPackages>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
