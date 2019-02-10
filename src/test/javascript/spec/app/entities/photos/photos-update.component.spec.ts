/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IdbTestModule } from '../../../test.module';
import { PhotosUpdateComponent } from 'app/entities/photos/photos-update.component';
import { PhotosService } from 'app/entities/photos/photos.service';
import { Photos } from 'app/shared/model/photos.model';

describe('Component Tests', () => {
    describe('Photos Management Update Component', () => {
        let comp: PhotosUpdateComponent;
        let fixture: ComponentFixture<PhotosUpdateComponent>;
        let service: PhotosService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IdbTestModule],
                declarations: [PhotosUpdateComponent]
            })
                .overrideTemplate(PhotosUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PhotosUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhotosService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Photos(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.photos = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Photos();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.photos = entity;
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
