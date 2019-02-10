import { Moment } from 'moment';
import { IChapters } from 'app/shared/model/chapters.model';

export const enum UserRoles {
    ADMIN = 'ADMIN',
    USER = 'USER',
    SUPERADMIN = 'SUPERADMIN'
}

export interface IIDBUser {
    id?: number;
    email?: string;
    password?: string;
    activated?: boolean;
    accessToken?: string;
    sessionToken?: string;
    lastLoginDate?: Moment;
    lastDeactivatedDate?: Moment;
    userRoles?: UserRoles;
    verified?: boolean;
    verificationMethod?: string;
    isReportedScam?: boolean;
    lastLogout?: Moment;
    lastActivatedDate?: Moment;
    createdDate?: Moment;
    createdBy?: string;
    lastUpdatedDate?: Moment;
    lastUpdatedBy?: string;
    initiatedChapters?: IChapters[];
    partnerChapters?: IChapters[];
    userProfileId?: number;
    userAccountId?: number;
}

export class IDBUser implements IIDBUser {
    constructor(
        public id?: number,
        public email?: string,
        public password?: string,
        public activated?: boolean,
        public accessToken?: string,
        public sessionToken?: string,
        public lastLoginDate?: Moment,
        public lastDeactivatedDate?: Moment,
        public userRoles?: UserRoles,
        public verified?: boolean,
        public verificationMethod?: string,
        public isReportedScam?: boolean,
        public lastLogout?: Moment,
        public lastActivatedDate?: Moment,
        public createdDate?: Moment,
        public createdBy?: string,
        public lastUpdatedDate?: Moment,
        public lastUpdatedBy?: string,
        public initiatedChapters?: IChapters[],
        public partnerChapters?: IChapters[],
        public userProfileId?: number,
        public userAccountId?: number
    ) {
        this.activated = this.activated || false;
        this.verified = this.verified || false;
        this.isReportedScam = this.isReportedScam || false;
    }
}
