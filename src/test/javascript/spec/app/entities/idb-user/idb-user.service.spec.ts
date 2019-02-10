/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IDBUserService } from 'app/entities/idb-user/idb-user.service';
import { IIDBUser, IDBUser, UserRoles } from 'app/shared/model/idb-user.model';

describe('Service Tests', () => {
    describe('IDBUser Service', () => {
        let injector: TestBed;
        let service: IDBUserService;
        let httpMock: HttpTestingController;
        let elemDefault: IIDBUser;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(IDBUserService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new IDBUser(
                0,
                'AAAAAAA',
                'AAAAAAA',
                false,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                UserRoles.ADMIN,
                false,
                'AAAAAAA',
                false,
                currentDate,
                currentDate,
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
                        lastLoginDate: currentDate.format(DATE_FORMAT),
                        lastDeactivatedDate: currentDate.format(DATE_FORMAT),
                        lastLogout: currentDate.format(DATE_FORMAT),
                        lastActivatedDate: currentDate.format(DATE_FORMAT),
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

            it('should create a IDBUser', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        lastLoginDate: currentDate.format(DATE_FORMAT),
                        lastDeactivatedDate: currentDate.format(DATE_FORMAT),
                        lastLogout: currentDate.format(DATE_FORMAT),
                        lastActivatedDate: currentDate.format(DATE_FORMAT),
                        createdDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        lastLoginDate: currentDate,
                        lastDeactivatedDate: currentDate,
                        lastLogout: currentDate,
                        lastActivatedDate: currentDate,
                        createdDate: currentDate,
                        lastUpdatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new IDBUser(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a IDBUser', async () => {
                const returnedFromService = Object.assign(
                    {
                        email: 'BBBBBB',
                        password: 'BBBBBB',
                        activated: true,
                        accessToken: 'BBBBBB',
                        sessionToken: 'BBBBBB',
                        lastLoginDate: currentDate.format(DATE_FORMAT),
                        lastDeactivatedDate: currentDate.format(DATE_FORMAT),
                        userRoles: 'BBBBBB',
                        verified: true,
                        verificationMethod: 'BBBBBB',
                        isReportedScam: true,
                        lastLogout: currentDate.format(DATE_FORMAT),
                        lastActivatedDate: currentDate.format(DATE_FORMAT),
                        createdDate: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        lastUpdatedDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedBy: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        lastLoginDate: currentDate,
                        lastDeactivatedDate: currentDate,
                        lastLogout: currentDate,
                        lastActivatedDate: currentDate,
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

            it('should return a list of IDBUser', async () => {
                const returnedFromService = Object.assign(
                    {
                        email: 'BBBBBB',
                        password: 'BBBBBB',
                        activated: true,
                        accessToken: 'BBBBBB',
                        sessionToken: 'BBBBBB',
                        lastLoginDate: currentDate.format(DATE_FORMAT),
                        lastDeactivatedDate: currentDate.format(DATE_FORMAT),
                        userRoles: 'BBBBBB',
                        verified: true,
                        verificationMethod: 'BBBBBB',
                        isReportedScam: true,
                        lastLogout: currentDate.format(DATE_FORMAT),
                        lastActivatedDate: currentDate.format(DATE_FORMAT),
                        createdDate: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        lastUpdatedDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedBy: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        lastLoginDate: currentDate,
                        lastDeactivatedDate: currentDate,
                        lastLogout: currentDate,
                        lastActivatedDate: currentDate,
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

            it('should delete a IDBUser', async () => {
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
