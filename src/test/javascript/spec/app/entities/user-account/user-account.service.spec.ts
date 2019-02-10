/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { UserAccountService } from 'app/entities/user-account/user-account.service';
import { IUserAccount, UserAccount, AccountType } from 'app/shared/model/user-account.model';

describe('Service Tests', () => {
    describe('UserAccount Service', () => {
        let injector: TestBed;
        let service: UserAccountService;
        let httpMock: HttpTestingController;
        let elemDefault: IUserAccount;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(UserAccountService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new UserAccount(
                0,
                0,
                currentDate,
                currentDate,
                0,
                'AAAAAAA',
                AccountType.Paid,
                0,
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
                        currPackageStartDate: currentDate.format(DATE_FORMAT),
                        currPackageEndDate: currentDate.format(DATE_FORMAT),
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

            it('should create a UserAccount', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        currPackageStartDate: currentDate.format(DATE_FORMAT),
                        currPackageEndDate: currentDate.format(DATE_FORMAT),
                        createdDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        currPackageStartDate: currentDate,
                        currPackageEndDate: currentDate,
                        createdDate: currentDate,
                        lastUpdatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new UserAccount(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a UserAccount', async () => {
                const returnedFromService = Object.assign(
                    {
                        remainingChapters: 1,
                        currPackageStartDate: currentDate.format(DATE_FORMAT),
                        currPackageEndDate: currentDate.format(DATE_FORMAT),
                        userDiscount: 1,
                        activated: 'BBBBBB',
                        accountType: 'BBBBBB',
                        perDayChapterLimit: 1,
                        createdDate: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        lastUpdatedDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedBy: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        currPackageStartDate: currentDate,
                        currPackageEndDate: currentDate,
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

            it('should return a list of UserAccount', async () => {
                const returnedFromService = Object.assign(
                    {
                        remainingChapters: 1,
                        currPackageStartDate: currentDate.format(DATE_FORMAT),
                        currPackageEndDate: currentDate.format(DATE_FORMAT),
                        userDiscount: 1,
                        activated: 'BBBBBB',
                        accountType: 'BBBBBB',
                        perDayChapterLimit: 1,
                        createdDate: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        lastUpdatedDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedBy: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        currPackageStartDate: currentDate,
                        currPackageEndDate: currentDate,
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

            it('should delete a UserAccount', async () => {
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
