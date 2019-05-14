import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NodeStringDecoder } from 'string_decoder';

@Component({
    selector: 'jhi-anon',
    templateUrl: './anon.component.html',
    styleUrls: ['anon.scss']
})
export class AnonComponent implements OnInit {
    listMatches: any;
    prefs: IMoodsPrefs;
    cities: string[] = ['Mumbai', 'Pune'];
    genders: any[] = [{ value: 'MALE', color: 'primary', checked: false }, { value: 'FEMALE', color: 'accent', checked: false }];
    genderPrefs: any[] = [{ value: 'MALE', color: 'primary', checked: false }, { value: 'FEMALE', color: 'accent', checked: false }];
    moods: any[] = [
        { value: 'TRAVEL PARTNER', color: 'primary', checked: false },
        { value: 'PARTY BUDDY', color: 'primary', checked: false },
        { value: 'HOOKUPS', color: 'accent', checked: false },
        { value: 'CASUAL RELATIONSHIPS', color: 'accent', checked: false },
        { value: 'BLIND DATE', color: 'accent', checked: false },
        { value: 'LIVE IN', color: 'accent', checked: false },
        { value: 'PERSONAL SERVICES', color: 'accent', checked: false },
        { value: 'LONG TERM RELATIONSHIPS', color: 'accent', checked: false },
        { value: 'NO STRINGS ATTACHED', color: 'accent', checked: false },
        { value: 'FRIENDS WITH BENEFITS', color: 'accent', checked: false },
        { value: 'OPEN FOR ANY RELATIOSHIP', color: 'accent', checked: false },
        { value: 'CASUAL MEETUPS', color: 'accent', checked: false },
        { value: 'COMPANION', color: 'accent', checked: false },
        { value: 'PHONE & CAM', color: 'accent', checked: false },
        { value: 'CLUBS AND BARS BUDDY', color: 'accent', checked: false }
    ];
    selectedMood: string;
    constructor(private router: Router) {}

    ngOnInit() {
        this.prefs = new MoodsPrefs();
        this.prefs.city = '';
    }

    onSubmit() {
        console.log('options :: ' + JSON.stringify(this.prefs));
        let routeStr = '/';
        if (this.prefs.gender && this.prefs.gender === 'MALE') {
            routeStr = routeStr + 'men-seeking-for';
            if (this.prefs.genderPref && this.prefs.genderPref === 'FEMALE') {
                routeStr = routeStr + '-women';
            }
        } else if (this.prefs.gender && this.prefs.gender === 'FEMALE') {
            routeStr = routeStr + 'women-seeking-for';
            if (this.prefs.genderPref && this.prefs.genderPref === 'FEMALE') {
                routeStr = routeStr + '-men';
            }
        } else {
            routeStr = routeStr + 'men-and-women-seeking-for';
        }
        if (this.prefs.city) {
            routeStr = routeStr + '/' + this.prefs.city;
        }
        console.log('router link :: ' + routeStr);
        this.router.navigate([routeStr]);
    }

    createOptions(): any {
        let options: any;
        if (this.prefs.gender === 'MALE') {
        }
        return options;
    }

    onMatchesFound(listMatches: any) {
        console.log('msg from child :: ' + listMatches);
        listMatches = [
            {
                screenName: 'Maya',
                age: 35,
                profilePic: '',
                pics: []
            },
            {
                screenName: 'Riya',
                age: 25,
                profilePic: '',
                pics: []
            },
            {
                screenName: 'Riya',
                age: 25,
                profilePic: '',
                pics: []
            },
            {
                screenName: 'Riya',
                age: 25,
                profilePic: '',
                pics: []
            }
        ];
        this.listMatches = listMatches;
    }

    selectGenderPref(chip, gender) {
        this.prefs.genderPref = gender.value;
        chip.select();
    }

    selectMood(chip, mood) {
        this.selectedMood = mood.value;
        chip.select();
    }

    selectGender(chip, gender) {
        this.prefs.gender = gender.value;
        chip.select();
    }
}

export interface IMoodsPrefs {
    tag?: string;
    genderPref?: string;
    city?: string;
    mood?: string;
    agePref?: number;
    gender?: string;
}

export class MoodsPrefs implements IMoodsPrefs {
    constructor(
        public tag?: string,
        public genderPref?: string,
        public city?: string,
        public mood?: string,
        public agePref?: number,
        public gender?: string
    ) {}
}
