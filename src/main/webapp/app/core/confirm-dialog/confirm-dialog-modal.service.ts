import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ConfirmDialogComponent } from './confirm-dialog.component';

@Injectable({
    providedIn: 'root'
})
export class ConfirmDialogModalService {
    constructor(private dialog: MatDialog) {}

    openConfirmDialog(msg) {
        return this.dialog.open(ConfirmDialogComponent, {
            width: '450px',
            panelClass: 'confirm-dialog-container',
            data: {
                message: msg
            }
        });
    }
}
