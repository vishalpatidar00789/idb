import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { AccountService, LoginModalService } from 'app/core';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

export interface DialogData {
    animal: string;
    name: string;
}

@Component({
    selector: 'jhi-payment',
    templateUrl: './payment.html',
    styleUrls: ['payment.scss']
})
export class PaymentComponent implements OnInit {
    isLinear = false;
    index = 0;
    regTab = false;
    selPkgTab = true;
    payTab = true;
    constructor(private eventManager: JhiEventManager) {}

    ngOnInit() {}
}
