import { Moment } from 'moment';
import { IPhotos } from 'app/shared/model/photos.model';
import { IProfileSettings } from 'app/shared/model/profile-settings.model';
import { ProfileStatus, Personalities, Interests, Offerings } from './preferences.model';

export interface IUserProfile {
    id?: number;
    screenName?: string;
    profilePicContentType?: string;
    profilePic?: any;
    gender?: string;
    dob?: Moment;
    age?: number;
    country?: string;
    state?: string;
    city?: string;
    pincode?: string;
    personalities?: Personalities[];
    interests?: Interests[];
    offerings?: Offerings[];
    personalityOthers?: string[];
    interestOthers?: string[];
    offeringOthers?: string[];
    status?: ProfileStatus;
    createdDate?: Moment;
    createdBy?: string;
    lastUpdatedDate?: Moment;
    lastUpdatedBy?: string;
    userEmail?: string;
    userId?: number;
    pics?: IPhotos[];
    profileSettings?: IProfileSettings[];
}

export class UserProfile implements IUserProfile {
    constructor(
        public id?: number,
        public screenName?: string,
        public profilePicContentType?: string,
        public profilePic?: any,
        public gender?: string,
        public dob?: Moment,
        public age?: number,
        public country?: string,
        public state?: string,
        public city?: string,
        public pincode?: string,
        public personalities?: Personalities[],
        public interests?: Interests[],
        public offerings?: Offerings[],
        public personalityOthers?: string[],
        public interestOthers?: string[],
        public offeringOthers?: string[],
        public status?: ProfileStatus,
        public createdDate?: Moment,
        public createdBy?: string,
        public lastUpdatedDate?: Moment,
        public lastUpdatedBy?: string,
        public userEmail?: string,
        public userId?: number,
        public pics?: IPhotos[],
        public profileSettings?: IProfileSettings[]
    ) {
        this.gender = 'male';
    }
}

export interface City {
    id: string;
    name: string;
}

/** list of citys */
export const Cities: City[] = [
    { id: 'mumbai', name: 'Mumbai' },
    { id: 'pune', name: 'Pune' },
    { id: 'delhi', name: 'Delhi' },
    { id: 'chennai', name: 'Chennai' }
];

export class PreferenceList {
    name: string;
    isDefault: boolean;
    isSelected: boolean;
    constructor() {
        this.isDefault = true;
        this.isSelected = false;
    }
}
