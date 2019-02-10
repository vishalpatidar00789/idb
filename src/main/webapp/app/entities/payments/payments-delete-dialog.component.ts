import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPayments } from 'app/shared/model/payments.model';
import { PaymentsService } from './payments.service';

@Component({
    selector: 'jhi-payments-delete-dialog',
    templateUrl: './payments-delete-dialog.component.html'
})
export class PaymentsDeleteDialogComponent {
    payments: IPayments;

    constructor(protected paymentsService: PaymentsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paymentsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'paymentsListModification',
                content: 'Deleted an payments'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payments-delete-popup',
    template: ''
})
export class PaymentsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ payments }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PaymentsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.payments = payments;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/payments', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/payments', { outlets: { popup: null } }]);
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
