/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IdbTestModule } from '../../../test.module';
import { IDBUserDetailComponent } from 'app/entities/idb-user/idb-user-detail.component';
import { IDBUser } from 'app/shared/model/idb-user.model';

describe('Component Tests', () => {
    describe('IDBUser Management Detail Component', () => {
        let comp: IDBUserDetailComponent;
        let fixture: ComponentFixture<IDBUserDetailComponent>;
        const route = ({ data: of({ iDBUser: new IDBUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IdbTestModule],
                declarations: [IDBUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IDBUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IDBUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.iDBUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
