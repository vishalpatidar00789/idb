/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IdbTestModule } from '../../../test.module';
import { IDBUserUpdateComponent } from 'app/entities/idb-user/idb-user-update.component';
import { IDBUserService } from 'app/entities/idb-user/idb-user.service';
import { IDBUser } from 'app/shared/model/idb-user.model';

describe('Component Tests', () => {
    describe('IDBUser Management Update Component', () => {
        let comp: IDBUserUpdateComponent;
        let fixture: ComponentFixture<IDBUserUpdateComponent>;
        let service: IDBUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IdbTestModule],
                declarations: [IDBUserUpdateComponent]
            })
                .overrideTemplate(IDBUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IDBUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IDBUserService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new IDBUser(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.iDBUser = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new IDBUser();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.iDBUser = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
