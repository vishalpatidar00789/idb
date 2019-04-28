import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
    selector: 'jhi-banner',
    templateUrl: './banner.component.html',
    styleUrls: ['banner.scss']
})
export class BannerComponent implements OnInit {
    constructor(private router: Router) {}

    ngOnInit() {}

    getStarted() {
        this.router.navigate(['/register']);
    }
}
