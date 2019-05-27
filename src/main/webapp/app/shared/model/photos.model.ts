import { Moment } from 'moment';

export interface IPhotos {
    id?: number;
    imageContentType?: string;
    image?: any;
    isProfilePicture?: boolean;
    orderInProfile?: number;
    createdDate?: Moment;
    createdBy?: string;
    lastUpdatedDate?: Moment;
    lastUpdatedBy?: string;
    userProfileId?: number;
}

export class Photos implements IPhotos {
    constructor(
        public id?: number,
        public imageContentType?: string,
        public image?: any,
        public isProfilePicture?: boolean,
        public orderInProfile?: number,
        public createdDate?: Moment,
        public createdBy?: string,
        public lastUpdatedDate?: Moment,
        public lastUpdatedBy?: string,
        public userProfileId?: number
    ) {}
}
