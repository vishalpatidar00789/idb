import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChapters } from 'app/shared/model/chapters.model';

@Component({
    selector: 'jhi-chapters-detail',
    templateUrl: './chapters-detail.component.html'
})
export class ChaptersDetailComponent implements OnInit {
    chapters: IChapters;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chapters }) => {
            this.chapters = chapters;
        });
    }

    previousState() {
        window.history.back();
    }
}
