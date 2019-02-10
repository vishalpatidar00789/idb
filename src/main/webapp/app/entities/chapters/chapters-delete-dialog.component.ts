import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChapters } from 'app/shared/model/chapters.model';
import { ChaptersService } from './chapters.service';

@Component({
    selector: 'jhi-chapters-delete-dialog',
    templateUrl: './chapters-delete-dialog.component.html'
})
export class ChaptersDeleteDialogComponent {
    chapters: IChapters;

    constructor(protected chaptersService: ChaptersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chaptersService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'chaptersListModification',
                content: 'Deleted an chapters'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-chapters-delete-popup',
    template: ''
})
export class ChaptersDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chapters }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ChaptersDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.chapters = chapters;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/chapters', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/chapters', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
