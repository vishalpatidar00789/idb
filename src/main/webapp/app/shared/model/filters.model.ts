import { Moment } from 'moment';
import { IPhotos } from 'app/shared/model/photos.model';
import { IProfileSettings } from 'app/shared/model/profile-settings.model';
import { ProfileStatus, Personalities, Interests, Offerings } from './preferences.model';

export interface IFilters {
    agePref?: number;
    country?: string;
    city?: string;
    genderPref?: string;
}

export class Filters implements IFilters {
    constructor(
        public id?: number,
        public screenName?: string,
        public profilePicContentType?: string,
        public profilePic?: any,
        public gender?: string,
        public dob?: string,
        public age?: number,
        public agePref?: number,
        public country?: string,
        public state?: string,
        public city?: string,
        public pincode?: string,
        public personality?: Personalities,
        public interests?: Interests,
        public offersings?: Offerings,
        public status?: ProfileStatus,
        public createdDate?: Moment,
        public createdBy?: string,
        public lastUpdatedDate?: Moment,
        public lastUpdatedBy?: string,
        public userEmail?: string,
        public userId?: number,
        public pics?: IPhotos[],
        public profileSettings?: IProfileSettings[],
        public genderPref?: string
    ) {
        this.gender = 'MALE';
        this.genderPref = 'FEMALE';
    }
}
