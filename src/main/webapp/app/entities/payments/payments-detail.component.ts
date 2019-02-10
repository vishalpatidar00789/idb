import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPayments } from 'app/shared/model/payments.model';

@Component({
    selector: 'jhi-payments-detail',
    templateUrl: './payments-detail.component.html'
})
export class PaymentsDetailComponent implements OnInit {
    payments: IPayments;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ payments }) => {
            this.payments = payments;
        });
    }

    previousState() {
        window.history.back();
    }
}
