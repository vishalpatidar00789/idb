import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfileSettings } from 'app/shared/model/profile-settings.model';
import { ProfileSettingsService } from './profile-settings.service';

@Component({
    selector: 'jhi-profile-settings-delete-dialog',
    templateUrl: './profile-settings-delete-dialog.component.html'
})
export class ProfileSettingsDeleteDialogComponent {
    profileSettings: IProfileSettings;

    constructor(
        protected profileSettingsService: ProfileSettingsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.profileSettingsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'profileSettingsListModification',
                content: 'Deleted an profileSettings'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-profile-settings-delete-popup',
    template: ''
})
export class ProfileSettingsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ profileSettings }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProfileSettingsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.profileSettings = profileSettings;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/profile-settings', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/profile-settings', { outlets: { popup: null } }]);
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
