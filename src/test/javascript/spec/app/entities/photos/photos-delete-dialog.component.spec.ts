/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IdbTestModule } from '../../../test.module';
import { PhotosDeleteDialogComponent } from 'app/entities/photos/photos-delete-dialog.component';
import { PhotosService } from 'app/entities/photos/photos.service';

describe('Component Tests', () => {
    describe('Photos Management Delete Component', () => {
        let comp: PhotosDeleteDialogComponent;
        let fixture: ComponentFixture<PhotosDeleteDialogComponent>;
        let service: PhotosService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IdbTestModule],
                declarations: [PhotosDeleteDialogComponent]
            })
                .overrideTemplate(PhotosDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PhotosDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhotosService);
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
