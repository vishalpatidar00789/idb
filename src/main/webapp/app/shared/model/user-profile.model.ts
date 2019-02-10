import { Moment } from 'moment';
import { IPhotos } from 'app/shared/model/photos.model';
import { IProfileSettings } from 'app/shared/model/profile-settings.model';

export const enum Personalities {
    Introvert = 'Introvert',
    Extrovert = 'Extrovert',
    Thinker = 'Thinker',
    Romantic = 'Romantic',
    Observer = 'Observer',
    Cool = 'Cool',
    Helping = 'Helping'
}

export const enum Interests {
    Travelling = 'Travelling',
    Reading = 'Reading',
    Parties = 'Parties',
    Movies = 'Movies',
    Music = 'Music',
    Sports = 'Sports',
    Fitness = 'Fitness'
}

export const enum Offerings {
    BlindDate = 'BlindDate',
    ShortDate = 'ShortDate',
    Hookups = 'Hookups',
    CasualRelationships = 'CasualRelationships',
    Chat = 'Chat',
    VideoChat = 'VideoChat'
}

export const enum ProfileStatus {
    Dating = 'Dating',
    Waiting = 'Waiting',
    BlindDate = 'BlindDate',
    Offline = 'Offline'
}

export interface IUserProfile {
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
}

export class UserProfile implements IUserProfile {
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
        public profileSettings?: IProfileSettings[]
    ) {}
}
