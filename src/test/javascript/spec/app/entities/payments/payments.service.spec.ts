/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PaymentsService } from 'app/entities/payments/payments.service';
import { IPayments, Payments, PaymentStatus } from 'app/shared/model/payments.model';

describe('Service Tests', () => {
    describe('Payments Service', () => {
        let injector: TestBed;
        let service: PaymentsService;
        let httpMock: HttpTestingController;
        let elemDefault: IPayments;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PaymentsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Payments(
                0,
                'AAAAAAA',
                PaymentStatus.Paid,
                0,
                currentDate,
                currentDate,
                false,
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
                        initiatedDate: currentDate.format(DATE_FORMAT),
                        confirmDate: currentDate.format(DATE_FORMAT),
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

            it('should create a Payments', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        initiatedDate: currentDate.format(DATE_FORMAT),
                        confirmDate: currentDate.format(DATE_FORMAT),
                        createdDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        initiatedDate: currentDate,
                        confirmDate: currentDate,
                        createdDate: currentDate,
                        lastUpdatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Payments(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Payments', async () => {
                const returnedFromService = Object.assign(
                    {
                        vendor: 'BBBBBB',
                        status: 'BBBBBB',
                        paymentValue: 1,
                        initiatedDate: currentDate.format(DATE_FORMAT),
                        confirmDate: currentDate.format(DATE_FORMAT),
                        activated: true,
                        createdDate: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        lastUpdatedDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedBy: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        initiatedDate: currentDate,
                        confirmDate: currentDate,
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

            it('should return a list of Payments', async () => {
                const returnedFromService = Object.assign(
                    {
                        vendor: 'BBBBBB',
                        status: 'BBBBBB',
                        paymentValue: 1,
                        initiatedDate: currentDate.format(DATE_FORMAT),
                        confirmDate: currentDate.format(DATE_FORMAT),
                        activated: true,
                        createdDate: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        lastUpdatedDate: currentDate.format(DATE_FORMAT),
                        lastUpdatedBy: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        initiatedDate: currentDate,
                        confirmDate: currentDate,
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

            it('should delete a Payments', async () => {
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
