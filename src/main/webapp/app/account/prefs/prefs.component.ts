import { Component, OnInit, Output, EventEmitter } from '@angular/core';
// import { coerceNumberProperty } from '@angular/cdk/coercion';
import { AccountService, Account } from 'app/core';
import { Router } from '@angular/router';
import { IFilters, Filters } from 'app/shared/model/filters.model';

@Component({
    selector: 'jhi-prefs',
    templateUrl: './prefs.component.html',
    styleUrls: ['prefs.style.scss']
})
export class PrefsComponent implements OnInit {
    account: Account;
    prefs: IFilters;
    mchecked = true;
    fchecked = false;
    indeterminate = false;
    labelPosition = 'after';
    disabled = false;
    autoTicks = false;
    invert = false;
    max = 100;
    min = 0;
    showTicks = false;
    // step = 1;
    thumbLabel = false;
    value = 0;
    vertical = false;
    genders: any[] = [{ value: 'MALE', color: 'primary', checked: false }, { value: 'FEMALE', color: 'accent', checked: false }];
    genderPrefs: any[] = [{ value: 'MALE', color: 'primary', checked: false }, { value: 'FEMALE', color: 'accent', checked: false }];
    types: any[] = [
        { value: 'ROMANTIC' },
        { value: 'SLIM' },
        { value: 'EXTROVERT' },
        { value: 'HUMEROUS' },
        { value: 'BOLD' },
        { value: 'ROMANTIC' }
    ];
    cities: string[] = ['Mumbai', 'Pune'];
    mcolor = 'black';
    fcolor = 'accent';
    constructor(private accountService: AccountService, private router: Router) {}

    ngOnInit() {
        // check for whether the current user is logged in or not
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });

        if (!this.account) {
            this.account = undefined;
        }
        this.prefs = new Filters();
        this.prefs.city = '';
        this.prefs.genderPref = 'FEMALE';
        this.maleSelected = true;
        this.femaleSelected = false;
        // if(this.femaleSelected){this.femaleSelected = true} else {this.femaleSelected = false}
    }
    maleSelected: Boolean = true;
    femaleSelected: Boolean = false;
    panelOpenState: Boolean = true;

    onSubmit(): void {
        console.log('prefs :: ' + this.prefs);
        // this.listMatches.emit('hi these are your matches');
        // this.panelOpenState = false;
        // update preferences into user profile and on success navigate to dashboard
        this.accountService.updatePreferences(this.prefs);
        this.router.navigate(['/dashboard']);
    }

    onChangeOfGender(gender) {
        console.log('selected val ' + JSON.stringify(gender.value));
        gender.checked = !gender.checked;
        // this.prefs.gender = gender.value;
    }

    selectGender(chip, gender) {
        // this.prefs.gender = gender.value;
        chip.select();
    }

    selectGenderPref(chip, gender) {
        this.prefs.genderPref = gender.value;
        chip.select();
    }

    formatLabel(value: number | null) {
        if (!value) {
            return 0;
        }

        if (value >= 1000) {
            return Math.round(value / 100) + 'Y';
        }

        return value;
    }

    step = 0;

    setStep(index: number) {
        this.step = index;
    }

    nextStep() {
        this.step++;
    }

    prevStep() {
        this.step--;
    }
}
