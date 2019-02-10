import { Moment } from 'moment';

export const enum ChapterStatus {
    Open = 'Open',
    Accepted = 'Accepted',
    Started = 'Started',
    Hold = 'Hold',
    Close = 'Close'
}

export interface IChapters {
    id?: number;
    status?: ChapterStatus;
    activated?: string;
    createdDate?: Moment;
    createdBy?: string;
    lastUpdatedDate?: Moment;
    lastUpdatedBy?: string;
    initiatorEmail?: string;
    initiatorId?: number;
    partnerEmail?: string;
    partnerId?: number;
}

export class Chapters implements IChapters {
    constructor(
        public id?: number,
        public status?: ChapterStatus,
        public activated?: string,
        public createdDate?: Moment,
        public createdBy?: string,
        public lastUpdatedDate?: Moment,
        public lastUpdatedBy?: string,
        public initiatorEmail?: string,
        public initiatorId?: number,
        public partnerEmail?: string,
        public partnerId?: number
    ) {}
}
