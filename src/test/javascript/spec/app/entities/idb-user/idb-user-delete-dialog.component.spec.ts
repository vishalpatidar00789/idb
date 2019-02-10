/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IdbTestModule } from '../../../test.module';
import { IDBUserDeleteDialogComponent } from 'app/entities/idb-user/idb-user-delete-dialog.component';
import { IDBUserService } from 'app/entities/idb-user/idb-user.service';

describe('Component Tests', () => {
    describe('IDBUser Management Delete Component', () => {
        let comp: IDBUserDeleteDialogComponent;
        let fixture: ComponentFixture<IDBUserDeleteDialogComponent>;
        let service: IDBUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IdbTestModule],
                declarations: [IDBUserDeleteDialogComponent]
            })
                .overrideTemplate(IDBUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IDBUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IDBUserService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
