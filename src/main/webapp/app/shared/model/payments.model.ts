import { Moment } from 'moment';

export const enum PaymentStatus {
    Paid = 'Paid',
    Failed = 'Failed',
    Hold = 'Hold'
}

export interface IPayments {
    id?: number;
    vendor?: string;
    status?: PaymentStatus;
    paymentValue?: number;
    initiatedDate?: Moment;
    confirmDate?: Moment;
    activated?: boolean;
    createdDate?: Moment;
    createdBy?: string;
    lastUpdatedDate?: Moment;
    lastUpdatedBy?: string;
    userAccountId?: number;
    appliedPackageId?: number;
}

export class Payments implements IPayments {
    constructor(
        public id?: number,
        public vendor?: string,
        public status?: PaymentStatus,
        public paymentValue?: number,
        public initiatedDate?: Moment,
        public confirmDate?: Moment,
        public activated?: boolean,
        public createdDate?: Moment,
        public createdBy?: string,
        public lastUpdatedDate?: Moment,
        public lastUpdatedBy?: string,
        public userAccountId?: number,
        public appliedPackageId?: number
    ) {
        this.activated = this.activated || false;
    }
}
