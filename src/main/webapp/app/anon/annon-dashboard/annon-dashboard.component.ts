import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-annon-dashboard',
    templateUrl: './annon-dashboard.component.html',
    styleUrls: ['annon-dashboard.style.scss']
})
export class AnnonDashboardComponent implements OnInit {
    matches: any;
    constructor(private router: Router, private route: ActivatedRoute) {}
    ngOnInit() {
        this.matches = this.route.snapshot.data['matches'];
        console.log('route data :: ' + this.matches);
    }
}
