import { Moment } from 'moment';
import { IPayments } from 'app/shared/model/payments.model';

export const enum AccountType {
    Paid = 'Paid',
    Free = 'Free',
    Hold = 'Hold'
}

export interface IUserAccount {
    id?: number;
    remainingChapters?: number;
    currPackageStartDate?: Moment;
    currPackageEndDate?: Moment;
    userDiscount?: number;
    activated?: string;
    accountType?: AccountType;
    perDayChapterLimit?: number;
    createdDate?: Moment;
    createdBy?: string;
    lastUpdatedDate?: Moment;
    lastUpdatedBy?: string;
    userEmail?: string;
    userId?: number;
    currentPackageId?: number;
    payments?: IPayments[];
}

export class UserAccount implements IUserAccount {
    constructor(
        public id?: number,
        public remainingChapters?: number,
        public currPackageStartDate?: Moment,
        public currPackageEndDate?: Moment,
        public userDiscount?: number,
        public activated?: string,
        public accountType?: AccountType,
        public perDayChapterLimit?: number,
        public createdDate?: Moment,
        public createdBy?: string,
        public lastUpdatedDate?: Moment,
        public lastUpdatedBy?: string,
        public userEmail?: string,
        public userId?: number,
        public currentPackageId?: number,
        public payments?: IPayments[]
    ) {}
}
