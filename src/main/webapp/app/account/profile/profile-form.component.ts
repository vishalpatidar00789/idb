import { Component, OnInit, AfterViewInit, Renderer, ElementRef, ViewChild, OnDestroy } from '@angular/core';
import { StateStorageService, AccountService } from 'app/core';
import { ProfileService } from './profile.service';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { JhiEventManager } from 'ng-jhipster';
import { Router } from '@angular/router';
import { MatChip, MatIconRegistry, MatSelect, MatChipInputEvent, MAT_DATE_FORMATS } from '@angular/material';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { map, startWith, takeUntil, take } from 'rxjs/operators';
import { UserProfile, PreferenceList, City, Cities } from 'app/shared/model/user-profile.model';
import { CustomIconService } from 'app/shared/util/custom-icon.service';
import { DomSanitizer } from '@angular/platform-browser';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { TitleCasePipe } from '@angular/common';
import * as moment from 'moment';
import { ProfileStatus, Personalities, Interests, Offerings } from 'app/shared/model/preferences.model';

export const MY_FORMATS = {
    parse: {
        dateInput: 'LL'
    },
    display: {
        dateInput: 'LL',
        monthYearLabel: 'MMM YYYY',
        dateA11yLabel: 'LL',
        monthYearA11yLabel: 'MMMM YYYY'
    }
};
@Component({
    selector: 'idb-profile-form',
    templateUrl: './profile-form.component.html',
    styleUrls: ['./profile.scss'],
    providers: [TitleCasePipe, { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS }]
})
export class ProfileFormComponent implements OnInit, AfterViewInit, OnDestroy {
    account: Account;
    profileForm: FormGroup;
    userProfile: UserProfile;
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
    maxDate: moment.Moment;

    readonly separatorKeysCodes: number[] = [ENTER, COMMA];

    /** Subject that emits when the component has been destroyed. */
    protected _onDestroy = new Subject<void>();
    // ProfileStatus: typeof ProfileStatus = ProfileStatus;
    // static readonly ProfileStatus = ProfileStatus;

    constructor(
        private accountService: AccountService,
        private profileService: ProfileService,
        private fb: FormBuilder,
        private matIconRegistry: MatIconRegistry,
        private domSanitizer: DomSanitizer,
        private titlecasePipe: TitleCasePipe,
        private eventManager: JhiEventManager,
        private stateStorageService: StateStorageService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private router: Router
    ) {
        this.maxDate = moment().subtract(17, 'years');
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

        this.setStatusOptions();
        this.setCityDetails();
        this.setPreferenceList();
        this.setUserDetails();
    }

    setUserDetails() {
        this.userProfile = new UserProfile();
        // this.userProfile.screenName = 'vijay';
        // this.userProfile.dob = moment('1990-05-25T15:52:10.733Z');
        // this.userProfile.status = ProfileStatus.BlindDate;
        // this.userProfile.city = 'pune';
        // this.userProfile.personalities = [Personalities.Extrovert, Personalities.Cool];
        // this.userProfile.interests = [Interests.Travelling, Interests.Movies];
        // this.userProfile.offerings = [Offerings.CasualRelationships];
        // this.userProfile.personalityOthers = ['Kind'];
        // this.userProfile.interestOthers = ['BikeRide'];
        // this.userProfile.offeringOthers = ['TravelDate'];
        this.initForm(this.userProfile);
    }

    initForm(userProfile?: UserProfile) {
        const userId = userProfile && userProfile.id ? userProfile.id : null;
        const screenName = userProfile && userProfile.screenName ? userProfile.screenName : null;
        const status = userProfile && userProfile.status ? userProfile.status : null;
        const gender = userProfile && userProfile.gender ? userProfile.gender : null;
        const dob = userProfile && userProfile.dob ? userProfile.dob : null;
        const city = userProfile && userProfile.city ? userProfile.city : null;
        let personalitySelected = [];
        this.userProfile.personalityOthers = [];
        if (userProfile.personalities && userProfile.personalities.length > 0) {
            personalitySelected = userProfile.personalities.map(personalityData => {
                return personalityData;
            });
        }
        this.userProfile.personalityOthers.forEach(personalityData => {
            const personality = new PreferenceList();
            personality.name = personalityData;
            personality.isDefault = false;
            personality.isSelected = true;
            this.personalityList.push(personality);
            personalitySelected.push(personalityData);
        });
        let interestSelected = [];
        this.userProfile.interestOthers = [];
        if (userProfile.interests && userProfile.interests.length > 0) {
            interestSelected = userProfile.interests.map(interestData => {
                return interestData;
            });
        }
        this.userProfile.interestOthers.forEach(interestData => {
            const interest = new PreferenceList();
            interest.name = interestData;
            interest.isDefault = false;
            interest.isSelected = true;
            this.interestList.push(interest);
            interestSelected.push(interestData);
        });
        let offeringSelected = [];
        this.userProfile.offeringOthers = [];
        if (userProfile.offerings && userProfile.offerings.length > 0) {
            offeringSelected = userProfile.offerings.map(offeringData => {
                return offeringData;
            });
        }
        this.userProfile.offeringOthers.forEach(offeringData => {
            const offering = new PreferenceList();
            offering.name = offeringData;
            offering.isDefault = false;
            offering.isSelected = true;
            this.offeringList.push(offering);
            offeringSelected.push(offeringData);
        });
        this.profileForm = this.fb.group({
            id: userId,
            status: [status, Validators.required],
            screenName: [screenName, Validators.required],
            gender,
            dob: [{ value: dob, disabled: true }],
            city,
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

    setStatusOptions() {
        const statusKeys = Object.keys(ProfileStatus);
        statusKeys.forEach(statusKey => {
            const status: any = { id: '', value: '', color: '', icon: '', isSvg: null };
            status.id = statusKey;
            status.value = statusKey;
            status.color = 'primary';
            switch (statusKey) {
                case 'Dating':
                    status.icon = 'dating';
                    status.isSvg = true;
                    break;
                case 'Waiting':
                    status.icon = 'waiting';
                    status.isSvg = true;
                    break;
                case 'BlindDate':
                    status.icon = 'blind-date';
                    status.isSvg = true;
                    break;
                case 'Offline':
                    status.icon = 'remove_circle_outline';
                    status.isSvg = false;
                    break;
                default:
                    break;
            }
            status.color = 'primary';
            this.statusList.push(status);
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
        this.profileForm.controls['gender'].setValue(gender.id);
    }

    selectStatus(statusChip, status) {
        statusChip.select();
        this.profileForm.controls['status'].setValue(status.id);
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
            const tempPersonalities = this.profileForm.controls['personalities'].value;
            tempPersonalities.push(personality.name);
            this.profileForm.controls['personalities'].setValue(tempPersonalities);
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
        const tempPersonalities = this.profileForm.controls['personalities'].value;
        const indexRemove = tempPersonalities.indexOf(personality.name);
        if (indexRemove >= 0) {
            tempPersonalities.splice(indexRemove, 1);
            this.profileForm.controls['personalities'].setValue(tempPersonalities);
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
            const tempPersonalities = this.profileForm.controls['personalities'].value;
            if (personalityChip.selected) {
                personalityChip.deselect();
                personality.isSelected = false;
                const indexRemove = tempPersonalities.indexOf(personality.name);
                if (indexRemove >= 0) {
                    tempPersonalities.splice(indexRemove, 1);
                    this.profileForm.controls['personalities'].setValue(tempPersonalities);
                }
            } else {
                personalityChip.select();
                personality.isSelected = true;
                tempPersonalities.push(personality.name);
                this.profileForm.controls['personalities'].setValue(tempPersonalities);
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
            const tempInterests = this.profileForm.controls['interests'].value;
            tempInterests.push(interest.name);
            this.profileForm.controls['interests'].setValue(tempInterests);
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
        const tempInterests = this.profileForm.controls['interests'].value;
        const indexRemove = tempInterests.indexOf(interest.name);
        if (indexRemove >= 0) {
            tempInterests.splice(indexRemove, 1);
            this.profileForm.controls['interests'].setValue(tempInterests);
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
            const tempInterests = this.profileForm.controls['interests'].value;
            if (interestChip.selected) {
                interestChip.deselect();
                interest.isSelected = false;
                const indexRemove = tempInterests.indexOf(interest.name);
                if (indexRemove >= 0) {
                    tempInterests.splice(indexRemove, 1);
                    this.profileForm.controls['interests'].setValue(tempInterests);
                }
            } else {
                interestChip.select();
                interest.isSelected = false;
                tempInterests.push(interest.name);
                this.profileForm.controls['interests'].setValue(tempInterests);
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
            const tempOfferings = this.profileForm.controls['offerings'].value;
            tempOfferings.push(offering.name);
            this.profileForm.controls['offerings'].setValue(tempOfferings);
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
        const tempOfferings = this.profileForm.controls['offerings'].value;
        const indexRemove = tempOfferings.indexOf(offering.name);
        if (indexRemove >= 0) {
            tempOfferings.splice(indexRemove, 1);
            this.profileForm.controls['offerings'].setValue(tempOfferings);
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
            const tempOfferings = this.profileForm.controls['offerings'].value;
            if (offeringChip.selected) {
                offeringChip.deselect();
                offering.isSelected = false;
                const indexRemove = tempOfferings.indexOf(offering.name);
                if (indexRemove >= 0) {
                    tempOfferings.splice(indexRemove, 1);
                    this.profileForm.controls['offerings'].setValue(tempOfferings);
                }
            } else {
                offeringChip.select();
                offering.isSelected = true;
                tempOfferings.push(offering.name);
                this.profileForm.controls['offerings'].setValue(tempOfferings);
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
        console.log(this.profileForm.value);
        const userProfile = new UserProfile();
        userProfile.screenName = this.profileForm.get('screenName').value;
        userProfile.gender = this.profileForm.get('gender').value;
        userProfile.city = this.profileForm.get('city').value;
        userProfile.dob = this.profileForm.get('dob').value;
        if (userProfile.dob) {
            userProfile.age = moment().diff(userProfile.dob, 'years');
        }
        userProfile.status = <ProfileStatus>ProfileStatus[this.profileForm.get('status').value];

        // Personality Endpoint
        // const personalitiesList: string[] = this.profileForm.get('personalities').value;
        userProfile.personalities = [];
        userProfile.personalityOthers = [];
        const personalitiesDefault = this.personalityList.filter(personality => personality.isDefault && personality.isSelected);
        const personalityOthers = this.personalityList.filter(personality => !personality.isDefault);
        personalitiesDefault.forEach(personality => {
            userProfile.personalities.push(<Personalities>Personalities[personality.name]);
        });
        personalityOthers.forEach(personality => {
            userProfile.personalityOthers.push(personality.name);
        });

        // Interests Endpoint
        userProfile.interests = [];
        userProfile.interestOthers = [];
        const interestsDefault = this.interestList.filter(interest => interest.isDefault && interest.isSelected);
        const interestOthers = this.interestList.filter(interest => !interest.isDefault);
        interestsDefault.forEach(interest => {
            userProfile.interests.push(<Interests>Interests[interest.name]);
        });
        interestOthers.forEach(interest => {
            userProfile.interestOthers.push(interest.name);
        });

        // Offerings Endpoint
        userProfile.offerings = [];
        userProfile.offeringOthers = [];
        const offeringsDefault = this.offeringList.filter(offering => offering.isDefault && offering.isSelected);
        const offeringOthers = this.offeringList.filter(offering => !offering.isDefault);
        offeringsDefault.forEach(offering => {
            userProfile.offerings.push(<Offerings>Offerings[offering.name]);
        });
        offeringOthers.forEach(offering => {
            userProfile.offeringOthers.push(offering.name);
        });
        console.log(userProfile);
    }
}
