import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'jhi-matches',
    templateUrl: './matches.component.html',
    styles: []
})
export class MatchesComponent implements OnInit {
    // @Input() userFilters: any;

    constructor() {}

    ngOnInit() {
        console.log('in matches');
    }
}
