import { Moment } from 'moment';

export enum Personalities {
    Introvert = 'Introvert',
    Extrovert = 'Extrovert',
    Thinker = 'Thinker',
    Romantic = 'Romantic',
    Observer = 'Observer',
    Cool = 'Cool',
    Helping = 'Helping'
}

export enum Interests {
    Travelling = 'Travelling',
    Reading = 'Reading',
    Parties = 'Parties',
    Movies = 'Movies',
    Music = 'Music',
    Sports = 'Sports',
    Fitness = 'Fitness'
}

export enum Offerings {
    BlindDate = 'BlindDate',
    ShortDate = 'ShortDate',
    Hookups = 'Hookups',
    CasualRelationships = 'CasualRelationships',
    Chat = 'Chat',
    VideoChat = 'VideoChat'
}

export enum ProfileStatus {
    Dating = 'Dating',
    Waiting = 'Waiting',
    BlindDate = 'BlindDate',
    Offline = 'Offline'
}

export interface IPreferences {
    id?: number;
    gender?: string;
    city?: string;
    age?: number;
    personalities?: Personalities[];
    interests?: Interests[];
    offerings?: Offerings[];
    personalityOthers?: string[];
    interestOthers?: string[];
    offeringOthers?: string[];
    createdDate?: Moment;
    createdBy?: string;
    lastUpdatedDate?: Moment;
    lastUpdatedBy?: string;
    userEmail?: string;
}

export class Preferences implements IPreferences {
    constructor(
        public id?: number,
        public gender?: string,
        public age?: number,
        public city?: string,
        public personalities?: Personalities[],
        public interests?: Interests[],
        public offerings?: Offerings[],
        public personalityOthers?: string[],
        public interestOthers?: string[],
        public offeringOthers?: string[],
        public createdDate?: Moment,
        public createdBy?: string,
        public lastUpdatedDate?: Moment,
        public lastUpdatedBy?: string
    ) {
        this.gender = 'male';
    }
}
