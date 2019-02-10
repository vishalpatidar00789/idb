/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IdbTestModule } from '../../../test.module';
import { ProfileSettingsUpdateComponent } from 'app/entities/profile-settings/profile-settings-update.component';
import { ProfileSettingsService } from 'app/entities/profile-settings/profile-settings.service';
import { ProfileSettings } from 'app/shared/model/profile-settings.model';

describe('Component Tests', () => {
    describe('ProfileSettings Management Update Component', () => {
        let comp: ProfileSettingsUpdateComponent;
        let fixture: ComponentFixture<ProfileSettingsUpdateComponent>;
        let service: ProfileSettingsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IdbTestModule],
                declarations: [ProfileSettingsUpdateComponent]
            })
                .overrideTemplate(ProfileSettingsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProfileSettingsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfileSettingsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProfileSettings(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.profileSettings = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProfileSettings();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.profileSettings = entity;
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
