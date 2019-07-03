import { Component, OnInit, Output, EventEmitter, ViewChild, OnDestroy, AfterViewInit, ElementRef } from '@angular/core';
// import { coerceNumberProperty } from '@angular/cdk/coercion';
import { AccountService, Account } from 'app/core';
import { Router } from '@angular/router';
import { IFilters, Filters } from 'app/shared/model/filters.model';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ReplaySubject, Subject } from 'rxjs';
import { MatSelect, MatIconRegistry, MatChip, MatChipInputEvent } from '@angular/material';
import { DomSanitizer } from '@angular/platform-browser';
import { takeUntil, take } from 'rxjs/operators';
import { City, Cities, PreferenceList } from 'app/shared/model/user-profile.model';
import { TitleCasePipe } from '@angular/common';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { Interests, Personalities, Offerings, Preferences } from 'app/shared/model/preferences.model';

@Component({
    selector: 'idb-prefs',
    templateUrl: './prefs.component.html',
    styleUrls: ['./prefs.scss'],
    providers: [TitleCasePipe]
})
export class PrefsComponent implements OnInit, AfterViewInit, OnDestroy {
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
    mcolor = 'black';
    fcolor = 'accent';
    panelOpenState: Boolean = true;
    prefsForm: FormGroup;
    preferences: Preferences;
    personalityList: PreferenceList[];
    interestList: PreferenceList[];
    offeringList: PreferenceList[];
    genders: any[] = [
        { id: 'male', value: 'MALE', color: 'primary', icon: 'gender-male' },
        { id: 'female', value: 'FEMALE', color: 'accent', icon: 'gender-female' },
        { id: 'others', value: 'OTHERS', color: 'warn', icon: 'gender-transgender' }
    ];
    statusList: any[] = [];
    protected cities: City[] = Cities;
    /** control for the MatSelect filter keyword */
    public cityFilterCtrl: FormControl = new FormControl();
    /** list of cities filtered by search keyword */
    public filteredCities: ReplaySubject<City[]> = new ReplaySubject<City[]>(1);

    @ViewChild('citySelect') citySelect: MatSelect;
    @ViewChild('personalityFormField') personalityFormField: ElementRef;
    @ViewChild('personalityChipList') personalityChipList;
    @ViewChild('interestChipList') interestChipList;
    @ViewChild('offeringChipList') offeringChipList;
    showAddPersonality = false;
    showAddInterest = false;
    showAddOffering = false;

    readonly separatorKeysCodes: number[] = [ENTER, COMMA];

    /** Subject that emits when the component has been destroyed. */
    protected _onDestroy = new Subject<void>();
    constructor(
        private accountService: AccountService,
        private router: Router,
        private fb: FormBuilder,
        private matIconRegistry: MatIconRegistry,
        private domSanitizer: DomSanitizer,
        private titlecasePipe: TitleCasePipe
    ) {
        this.matIconRegistry.addSvgIcon(
            'gender-male',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/gender-male.svg')
        );
        this.matIconRegistry.addSvgIcon(
            'gender-female',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/gender-female.svg')
        );
        this.matIconRegistry.addSvgIcon(
            'gender-transgender',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/gender-transgender.svg')
        );
        this.matIconRegistry.addSvgIcon(
            'personality',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/personality.svg')
        );
        this.matIconRegistry.addSvgIcon(
            'offering',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/offering.svg')
        );
        this.matIconRegistry.addSvgIcon(
            'interest',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/interest.svg')
        );
        this.matIconRegistry.addSvgIcon('dating', this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/dating.svg'));
        this.matIconRegistry.addSvgIcon('waiting', this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/waiting.svg'));
        this.matIconRegistry.addSvgIcon(
            'blind-date',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/blind-date.svg')
        );
    }

    ngOnInit() {
        // check for whether the current user is logged in or not
        this.accountService
            .identity()
            .then(account => {
                console.log('auth done :: user logged in :: ' + JSON.stringify(account));
                if (account) {
                    this.account = account;
                }
            })
            .catch(() => {
                console.log('error while auth success event :: accessing account');
            });

        this.setCityDetails();
        this.setPreferenceList();
        this.setUserDetails();
    }

    setUserDetails() {
        this.preferences = new Preferences();
        // this.preferences.city = 'pune';
        // this.preferences.age = 21;
        // this.preferences.personalities = [Personalities.Extrovert, Personalities.Cool];
        // this.preferences.interests = [Interests.Travelling, Interests.Movies];
        // this.preferences.offerings = [Offerings.CasualRelationships];
        // this.preferences.personalityOthers = ['Kind'];
        // this.preferences.interestOthers = ['BikeRide'];
        // this.preferences.offeringOthers = ['TravelDate'];
        this.initForm(this.preferences);
    }

    initForm(preferences?: Preferences) {
        const userId = preferences && preferences.id ? preferences.id : null;
        const gender = preferences && preferences.gender ? preferences.gender : null;
        const city = preferences && preferences.city ? preferences.city : null;
        const age = preferences && preferences.age ? preferences.age : null;
        let personalitySelected = [];
        this.preferences.personalityOthers = [];
        if (preferences.personalities && preferences.personalities.length > 0) {
            personalitySelected = preferences.personalities.map(personalityData => {
                return personalityData;
            });
        }
        this.preferences.personalityOthers.forEach(personalityData => {
            const personality = new PreferenceList();
            personality.name = personalityData;
            personality.isDefault = false;
            personality.isSelected = true;
            this.personalityList.push(personality);
            personalitySelected.push(personalityData);
        });
        let interestSelected = [];
        this.preferences.interestOthers = [];
        if (preferences.interests && preferences.interests.length > 0) {
            interestSelected = preferences.interests.map(interestData => {
                return interestData;
            });
        }
        this.preferences.interestOthers.forEach(interestData => {
            const interest = new PreferenceList();
            interest.name = interestData;
            interest.isDefault = false;
            interest.isSelected = true;
            this.interestList.push(interest);
            interestSelected.push(interestData);
        });
        let offeringSelected = [];
        this.preferences.offeringOthers = [];
        if (preferences.offerings && preferences.offerings.length > 0) {
            offeringSelected = preferences.offerings.map(offeringData => {
                return offeringData;
            });
        }
        this.preferences.offeringOthers.forEach(offeringData => {
            const offering = new PreferenceList();
            offering.name = offeringData;
            offering.isDefault = false;
            offering.isSelected = true;
            this.offeringList.push(offering);
            offeringSelected.push(offeringData);
        });
        this.prefsForm = this.fb.group({
            id: userId,
            gender,
            city,
            age,
            personalities: [personalitySelected],
            interests: [interestSelected],
            offerings: [offeringSelected]
        });
    }

    setPreferenceList() {
        // Set Default value for Personality
        this.personalityList = [];
        const personalityKeys = Object.keys(Personalities);
        personalityKeys.forEach(personalityKey => {
            const personality = new PreferenceList();
            personality.name = personalityKey;
            this.personalityList.push(personality);
        });

        // Set Default value for Interest
        this.interestList = [];
        const interestKeys = Object.keys(Interests);
        interestKeys.forEach(interestKey => {
            const interest = new PreferenceList();
            interest.name = interestKey;
            this.interestList.push(interest);
        });

        // Set Default value for Offering
        this.offeringList = [];
        const offeringKeys = Object.keys(Offerings);
        offeringKeys.forEach(offeringKey => {
            const offering = new PreferenceList();
            offering.name = offeringKey;
            this.offeringList.push(offering);
        });
    }

    setCityDetails() {
        // load the initial city list
        this.filteredCities.next(this.cities.slice());

        // listen for search field value changes
        this.cityFilterCtrl.valueChanges.pipe(takeUntil(this._onDestroy)).subscribe(() => {
            this.filterCities();
        });
    }

    selectGender(genderChip, gender) {
        genderChip.select();
        this.prefsForm.controls['gender'].setValue(gender.id);
    }

    // Personality

    addPersonality(event: MatChipInputEvent): void {
        // if (!this.personalityFormField.nativeElement.classList.contains('mat-focused')) {
        //     this.showAddPersonality = false;
        // }
        // this.showAddPersonality = false;
        const input = event.input;
        const value = event.value;
        if ((value || '').trim()) {
            const personality = new PreferenceList();
            personality.name = this.titlecasePipe.transform(value.trim());
            personality.isDefault = false;
            personality.isSelected = true;
            this.personalityList.push(personality);
            const tempPersonalities = this.prefsForm.controls['personalities'].value;
            tempPersonalities.push(personality.name);
            this.prefsForm.controls['personalities'].setValue(tempPersonalities);
        }
        // Reset the input value
        if (input) {
            input.value = '';
        }
        if (this.personalityList.length > 10) {
            this.personalityChipList.errorState = true;
            this.showAddPersonality = false;
        }
    }

    removePersonality(personality: PreferenceList): void {
        const tempPersonalities = this.prefsForm.controls['personalities'].value;
        const indexRemove = tempPersonalities.indexOf(personality.name);
        if (indexRemove >= 0) {
            tempPersonalities.splice(indexRemove, 1);
            this.prefsForm.controls['personalities'].setValue(tempPersonalities);
        }
        const indexRemoveList = this.personalityList.indexOf(personality);
        if (indexRemoveList >= 0) {
            this.personalityList.splice(indexRemoveList, 1);
        }

        if (this.personalityList.length <= 10) {
            this.personalityChipList.errorState = false;
        }
    }
    selectPersonality(personalityChip: MatChip, personality: PreferenceList) {
        if (personality.isDefault) {
            const tempPersonalities = this.prefsForm.controls['personalities'].value;
            if (personalityChip.selected) {
                personalityChip.deselect();
                personality.isSelected = false;
                const indexRemove = tempPersonalities.indexOf(personality.name);
                if (indexRemove >= 0) {
                    tempPersonalities.splice(indexRemove, 1);
                    this.prefsForm.controls['personalities'].setValue(tempPersonalities);
                }
            } else {
                personalityChip.select();
                personality.isSelected = true;
                tempPersonalities.push(personality.name);
                this.prefsForm.controls['personalities'].setValue(tempPersonalities);
            }
        }
    }

    // Interest

    addInterest(event: MatChipInputEvent): void {
        // this.showAddInterest = false;
        const input = event.input;
        const value = event.value;
        if ((value || '').trim()) {
            const interest = new PreferenceList();
            interest.name = this.titlecasePipe.transform(value.trim());
            interest.isDefault = false;
            interest.isSelected = true;
            this.interestList.push(interest);
            const tempInterests = this.prefsForm.controls['interests'].value;
            tempInterests.push(interest.name);
            this.prefsForm.controls['interests'].setValue(tempInterests);
        }
        // Reset the input value
        if (input) {
            input.value = '';
        }
        if (this.interestList.length > 10) {
            this.interestChipList.errorState = true;
            this.showAddInterest = false;
        }
    }

    removeInterest(interest: PreferenceList): void {
        const tempInterests = this.prefsForm.controls['interests'].value;
        const indexRemove = tempInterests.indexOf(interest.name);
        if (indexRemove >= 0) {
            tempInterests.splice(indexRemove, 1);
            this.prefsForm.controls['interests'].setValue(tempInterests);
        }
        const indexRemoveList = this.interestList.indexOf(interest);
        if (indexRemoveList >= 0) {
            this.interestList.splice(indexRemoveList, 1);
        }

        if (this.interestList.length <= 10) {
            this.interestChipList.errorState = false;
        }
    }
    selectInterest(interestChip, interest: PreferenceList) {
        if (interest.isDefault) {
            const tempInterests = this.prefsForm.controls['interests'].value;
            if (interestChip.selected) {
                interestChip.deselect();
                interest.isSelected = false;
                const indexRemove = tempInterests.indexOf(interest.name);
                if (indexRemove >= 0) {
                    tempInterests.splice(indexRemove, 1);
                    this.prefsForm.controls['interests'].setValue(tempInterests);
                }
            } else {
                interestChip.select();
                interest.isSelected = false;
                tempInterests.push(interest.name);
                this.prefsForm.controls['interests'].setValue(tempInterests);
            }
        }
    }

    // Offerings

    addOffering(event: MatChipInputEvent): void {
        // this.showAddOffering = false;
        const input = event.input;
        const value = event.value;
        if ((value || '').trim()) {
            const offering = new PreferenceList();
            offering.name = this.titlecasePipe.transform(value.trim());
            offering.isDefault = false;
            this.offeringList.push(offering);
            const tempOfferings = this.prefsForm.controls['offerings'].value;
            tempOfferings.push(offering.name);
            this.prefsForm.controls['offerings'].setValue(tempOfferings);
        }
        // Reset the input value
        if (input) {
            input.value = '';
        }
        if (this.offeringList.length > 10) {
            this.offeringChipList.errorState = true;
            this.showAddOffering = false;
        }
    }

    removeOffering(offering: PreferenceList): void {
        const tempOfferings = this.prefsForm.controls['offerings'].value;
        const indexRemove = tempOfferings.indexOf(offering.name);
        if (indexRemove >= 0) {
            tempOfferings.splice(indexRemove, 1);
            this.prefsForm.controls['offerings'].setValue(tempOfferings);
        }
        const indexRemoveList = this.offeringList.indexOf(offering);
        if (indexRemoveList >= 0) {
            this.offeringList.splice(indexRemoveList, 1);
        }

        if (this.offeringList.length <= 10) {
            this.offeringChipList.errorState = false;
        }
    }
    selectOffering(offeringChip, offering: PreferenceList) {
        if (offering.isDefault) {
            const tempOfferings = this.prefsForm.controls['offerings'].value;
            if (offeringChip.selected) {
                offeringChip.deselect();
                offering.isSelected = false;
                const indexRemove = tempOfferings.indexOf(offering.name);
                if (indexRemove >= 0) {
                    tempOfferings.splice(indexRemove, 1);
                    this.prefsForm.controls['offerings'].setValue(tempOfferings);
                }
            } else {
                offeringChip.select();
                offering.isSelected = true;
                tempOfferings.push(offering.name);
                this.prefsForm.controls['offerings'].setValue(tempOfferings);
            }
        }
    }

    ngAfterViewInit() {
        this.setInitialValue();
    }

    ngOnDestroy() {
        this._onDestroy.next();
        this._onDestroy.complete();
    }
    /**
     * Sets the initial value after the filteredBanks are loaded initially
     */
    protected setInitialValue() {
        this.filteredCities
            .pipe(
                take(1),
                takeUntil(this._onDestroy)
            )
            .subscribe(() => {
                // setting the compareWith property to a comparison function
                // triggers initializing the selection according to the initial value of
                // the form control (i.e. _initializeSelection())
                // this needs to be done after the filteredBanks are loaded initially
                // and after the mat-option elements are available
                this.citySelect.compareWith = (a: City, b: City) => a && b && a.id === b.id;
            });
    }

    protected filterCities() {
        if (!this.cities) {
            return;
        }
        // get the search keyword
        let search = this.cityFilterCtrl.value;
        if (!search) {
            this.filteredCities.next(this.cities.slice());
            return;
        } else {
            search = search.toLowerCase();
        }
        // filter the cities
        this.filteredCities.next(this.cities.filter(city => city.name.toLowerCase().indexOf(search) > -1));
    }

    onSubmit() {
        console.log(this.prefsForm.value);
        const preferences = new Preferences();
        preferences.gender = this.prefsForm.get('gender').value;
        preferences.city = this.prefsForm.get('city').value;
        preferences.age = this.prefsForm.get('age').value;

        // Personality Endpoint
        // const personalitiesList: string[] = this.prefsForm.get('personalities').value;
        preferences.personalities = [];
        preferences.personalityOthers = [];
        const personalitiesDefault = this.personalityList.filter(personality => personality.isDefault && personality.isSelected);
        const personalityOthers = this.personalityList.filter(personality => !personality.isDefault);
        personalitiesDefault.forEach(personality => {
            preferences.personalities.push(<Personalities>Personalities[personality.name]);
        });
        personalityOthers.forEach(personality => {
            preferences.personalityOthers.push(personality.name);
        });

        // Interests Endpoint
        preferences.interests = [];
        preferences.interestOthers = [];
        const interestsDefault = this.interestList.filter(interest => interest.isDefault && interest.isSelected);
        const interestOthers = this.interestList.filter(interest => !interest.isDefault);
        interestsDefault.forEach(interest => {
            preferences.interests.push(<Interests>Interests[interest.name]);
        });
        interestOthers.forEach(interest => {
            preferences.interestOthers.push(interest.name);
        });

        // Offerings Endpoint
        preferences.offerings = [];
        preferences.offeringOthers = [];
        const offeringsDefault = this.offeringList.filter(offering => offering.isDefault && offering.isSelected);
        const offeringOthers = this.offeringList.filter(offering => !offering.isDefault);
        offeringsDefault.forEach(offering => {
            preferences.offerings.push(<Offerings>Offerings[offering.name]);
        });
        offeringOthers.forEach(offering => {
            preferences.offeringOthers.push(offering.name);
        });
        console.log(preferences);

        // this.listMatches.emit('hi these are your matches');
        // this.panelOpenState = false;
        // update preferences into user profile and on success navigate to dashboard
        // this.accountService.updatePreferences(this.prefs);
        // this.router.navigate(['/dashboard']);
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
