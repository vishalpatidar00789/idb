import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { MatIconRegistry, MatDialog, MatDialogConfig } from '@angular/material';
import { DomSanitizer } from '@angular/platform-browser';
import { PaymentComponent } from 'app/account/payment/payment.component';

@Component({
    selector: 'idb-match-holder',
    templateUrl: './match-holder.component.html',
    styleUrls: ['./match-holder.component.scss']
})
export class MatchHolderComponent implements OnInit {
    account: Account;
    slides: { image: string }[] = [];
    activeSlideIndex = 0;
    @Input() match: any;
    pause: true;
    constructor(private router: Router, public dialog: MatDialog, iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
        for (let i = 0; i < 3; i++) {
            this.addSlide();
        }

        // iconRegistry.addSvgIcon('more_vert', sanitizer.bypassSecurityTrustResourceUrl('../../content/images/more_vert.svg'));
    }

    ngOnInit() {}

    addSlide(): void {
        this.slides.push({
            image: `../../../content/images/${this.slides.length + 1}.jpg`
        });
    }
    requestDate(match) {
        console.log('match :: requested :: ' + match);
        // this.openDialog();
        this.router.navigate(['register']);
    }

    // openDialog(): void {
    //     const dialogRef = this.dialog.open(PaymentComponent, {
    //         width: '650px',
    //         height: '450px',
    //         data: { name: 'vishal', animal: 'lion' }
    //     });

    //     dialogRef.afterClosed().subscribe(result => {
    //         console.log('The dialog was closed');
    //         // this.animal = result;
    //     });
    // }
}
