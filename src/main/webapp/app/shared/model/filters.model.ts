import { Personalities, Interests, Offerings, ProfileStatus } from 'app/shared/model/user-profile.model';
import { Moment } from 'moment';
import { IPhotos } from 'app/shared/model/photos.model';
import { IProfileSettings } from 'app/shared/model/profile-settings.model';

export interface IFilters {
    id?: number;
    screenName?: string;
    profilePicContentType?: string;
    profilePic?: any;
    gender?: string;
    dob?: string;
    age?: number;
    country?: string;
    state?: string;
    city?: string;
    pincode?: string;
    personality?: Personalities;
    interests?: Interests;
    offersings?: Offerings;
    status?: ProfileStatus;
    createdDate?: Moment;
    createdBy?: string;
    lastUpdatedDate?: Moment;
    lastUpdatedBy?: string;
    userEmail?: string;
    userId?: number;
    pics?: IPhotos[];
    profileSettings?: IProfileSettings[];
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