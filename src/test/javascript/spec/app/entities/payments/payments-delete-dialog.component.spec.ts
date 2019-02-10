/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IdbTestModule } from '../../../test.module';
import { PaymentsDeleteDialogComponent } from 'app/entities/payments/payments-delete-dialog.component';
import { PaymentsService } from 'app/entities/payments/payments.service';

describe('Component Tests', () => {
    describe('Payments Management Delete Component', () => {
        let comp: PaymentsDeleteDialogComponent;
        let fixture: ComponentFixture<PaymentsDeleteDialogComponent>;
        let service: PaymentsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IdbTestModule],
                declarations: [PaymentsDeleteDialogComponent]
            })
                .overrideTemplate(PaymentsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaymentsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentsService);
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
