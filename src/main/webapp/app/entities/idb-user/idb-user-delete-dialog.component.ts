import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIDBUser } from 'app/shared/model/idb-user.model';
import { IDBUserService } from './idb-user.service';

@Component({
    selector: 'jhi-idb-user-delete-dialog',
    templateUrl: './idb-user-delete-dialog.component.html'
})
export class IDBUserDeleteDialogComponent {
    iDBUser: IIDBUser;

    constructor(protected iDBUserService: IDBUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.iDBUserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'iDBUserListModification',
                content: 'Deleted an iDBUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-idb-user-delete-popup',
    template: ''
})
export class IDBUserDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ iDBUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IDBUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.iDBUser = iDBUser;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/idb-user', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/idb-user', { outlets: { popup: null } }]);
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
