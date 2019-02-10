/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IdbTestModule } from '../../../test.module';
import { ProfileSettingsDetailComponent } from 'app/entities/profile-settings/profile-settings-detail.component';
import { ProfileSettings } from 'app/shared/model/profile-settings.model';

describe('Component Tests', () => {
    describe('ProfileSettings Management Detail Component', () => {
        let comp: ProfileSettingsDetailComponent;
        let fixture: ComponentFixture<ProfileSettingsDetailComponent>;
        const route = ({ data: of({ profileSettings: new ProfileSettings(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IdbTestModule],
                declarations: [ProfileSettingsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProfileSettingsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProfileSettingsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.profileSettings).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
