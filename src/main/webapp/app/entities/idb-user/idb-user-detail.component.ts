import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIDBUser } from 'app/shared/model/idb-user.model';

@Component({
    selector: 'jhi-idb-user-detail',
    templateUrl: './idb-user-detail.component.html'
})
export class IDBUserDetailComponent implements OnInit {
    iDBUser: IIDBUser;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ iDBUser }) => {
            this.iDBUser = iDBUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
