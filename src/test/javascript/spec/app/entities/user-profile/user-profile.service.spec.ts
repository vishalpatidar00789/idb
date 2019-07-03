/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';
import { Personalities, ProfileStatus, Interests, Offerings } from 'app/shared/model/preferences.model';

describe('Service Tests', () => {
    describe('UserProfile Service', () => {
        let injector: TestBed;
        let service: UserProfileService;
        let httpMock: HttpTestingController;
        let elemDefault: IUserProfile;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(UserProfileService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new UserProfile(
                0,
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                [Personalities.Introvert],
                [Interests.Travelling],
                [Offerings.BlindDate],
                [],
                [],
                [],
                ProfileStatus.Dating,
                currentDate,
                'AAAAAAA',
                currentDate,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a UserProfile', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastUpdatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new UserProfile(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a UserProfile', async () => {
                const returnedFromService = Object.assign(
                    {
                        screenName: 'BBBBBB',
                        profilePic: 'BBBBBB',
                        gender: 'BBBBBB',
                        dob: currentDate.format(DATE_FORMAT),
                        age: 1,
                        country: 'BBBBBB',
                        state: 'BBBBBB',
                        city: 'BBBBBB',
                        pincode: 'BBBBBB',
                        personality: 'BBBBBB',
                        interests: 'BBBBBB',
                        offersings: 'BBBBBB',
                        status: 'BBBBBB',
                        createdDate: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        lastUpdatedDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedBy: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastUpdatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of UserProfile', async () => {
                const returnedFromService = Object.assign(
                    {
                        screenName: 'BBBBBB',
                        profilePic: 'BBBBBB',
                        gender: 'BBBBBB',
                        dob: currentDate.format(DATE_FORMAT),
                        age: 1,
                        country: 'BBBBBB',
                        state: 'BBBBBB',
                        city: 'BBBBBB',
                        pincode: 'BBBBBB',
                        personality: 'BBBBBB',
                        interests: 'BBBBBB',
                        offersings: 'BBBBBB',
                        status: 'BBBBBB',
                        createdDate: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        lastUpdatedDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedBy: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastUpdatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a UserProfile', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
