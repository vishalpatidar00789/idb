import { Moment } from 'moment';
import { IPayments } from 'app/shared/model/payments.model';

export interface IPackages {
    id?: number;
    title?: string;
    price?: number;
    type?: string;
    expiry?: number;
    totalChapters?: number;
    discount?: number;
    activated?: string;
    createdDate?: Moment;
    createdBy?: string;
    lastUpdatedDate?: Moment;
    lastUpdatedBy?: string;
    payments?: IPayments[];
}

export class Packages implements IPackages {
    constructor(
        public id?: number,
        public title?: string,
        public price?: number,
        public type?: string,
        public expiry?: number,
        public totalChapters?: number,
        public discount?: number,
        public activated?: string,
        public createdDate?: Moment,
        public createdBy?: string,
        public lastUpdatedDate?: Moment,
        public lastUpdatedBy?: string,
        public payments?: IPayments[]
    ) {}
}
