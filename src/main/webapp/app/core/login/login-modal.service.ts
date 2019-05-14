import { Injectable } from '@angular/core';

import { JhiLoginModalComponent } from 'app/shared/login/login.component';
import { MatDialog } from '@angular/material';

@Injectable({ providedIn: 'root' })
export class LoginModalService {
    private isOpen = false;
    constructor(private dialog: MatDialog) {}

    open(isLogin): any {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        const dialogRef = this.dialog.open(JhiLoginModalComponent, {
            width: '550px',
            data: { isLogin: isLogin }
        });

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed' + JSON.stringify(result));
            // this.animal = result;
            this.isOpen = false;
        });

        return dialogRef;
    }

    // open(): NgbModalRef {
    //     if (this.isOpen) {
    //         return;
    //     }
    //     this.isOpen = true;
    //     const modalRef = this.modalService.open(JhiLoginModalComponent);
    //     modalRef.result.then(
    //         result => {
    //             this.isOpen = false;
    //         },
    //         reason => {
    //             this.isOpen = false;
    //         }
    //     );
    //     return modalRef;
    // }
}
