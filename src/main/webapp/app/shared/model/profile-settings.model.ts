import { Moment } from 'moment';

export interface IProfileSettings {
    id?: number;
    key?: string;
    isPublic?: boolean;
    createdDate?: Moment;
    createdBy?: string;
    lastUpdatedDate?: Moment;
    lastUpdatedBy?: string;
    userProfileId?: number;
}

export class ProfileSettings implements IProfileSettings {
    constructor(
        public id?: number,
        public key?: string,
        public isPublic?: boolean,
        public createdDate?: Moment,
        public createdBy?: string,
        public lastUpdatedDate?: Moment,
        public lastUpdatedBy?: string,
        public userProfileId?: number
    ) {
        this.isPublic = this.isPublic || false;
    }
}
