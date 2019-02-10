/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IdbTestModule } from '../../../test.module';
import { ChaptersDetailComponent } from 'app/entities/chapters/chapters-detail.component';
import { Chapters } from 'app/shared/model/chapters.model';

describe('Component Tests', () => {
    describe('Chapters Management Detail Component', () => {
        let comp: ChaptersDetailComponent;
        let fixture: ComponentFixture<ChaptersDetailComponent>;
        const route = ({ data: of({ chapters: new Chapters(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IdbTestModule],
                declarations: [ChaptersDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChaptersDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChaptersDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.chapters).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
