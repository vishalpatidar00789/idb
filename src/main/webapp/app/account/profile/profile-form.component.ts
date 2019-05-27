import { Component, OnInit, AfterViewInit, Renderer, ElementRef, ViewChild } from '@angular/core';
import { StateStorageService, AccountService } from 'app/core';
import { ProfileService } from './profile.service';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { JhiEventManager } from 'ng-jhipster';
import { Router } from '@angular/router';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatChip, MatIconRegistry } from '@angular/material';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { UserProfile } from 'app/shared/model/user-profile.model';
import { CustomIconService } from 'app/shared/util/custom-icon.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
    selector: 'idb-profile-form',
    templateUrl: './profile-form.component.html',
    styleUrls: ['./profile.scss']
})
export class ProfileFormComponent implements OnInit {
    account: Account;
    profileForm: FormGroup;
    userProfile: UserProfile;
    personalities: string[] = ['Introvert', 'Extrovert', 'Thinker', 'Romantic', 'Observer', 'Cool', 'Helping'];
    interests: string[] = ['Travelling', 'Reading', 'Parties', 'Movies', 'Music', 'Sports', 'Fitness'];
    offerings: string[] = ['BlindDate', 'ShortDate', 'Hookups', 'CasualRelationships', 'Chat', 'VideoChat'];
    genders: any[] = [
        { value: 'MALE', color: 'primary', icon: 'gender-male' },
        { value: 'FEMALE', color: 'accent', icon: 'gender-female' },
        { value: 'OTHERS', color: 'warn', icon: 'gender-transgender' }
    ];
    cities: string[] = ['Mumbai', 'Pune'];

    constructor(
        private accountService: AccountService,
        private profileService: ProfileService,
        private fb: FormBuilder,
        private matIconRegistry: MatIconRegistry,
        private domSanitizer: DomSanitizer,
        private eventManager: JhiEventManager,
        private stateStorageService: StateStorageService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private router: Router
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
            'offerings',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/offerings.svg')
        );
        this.matIconRegistry.addSvgIcon(
            'interest',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/interest.svg')
        );
        this.userProfile = new UserProfile();
        this.initForm(this.userProfile);
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
    }

    initForm(userProfile?: UserProfile) {
        this.profileForm = this.fb.group({
            id: userProfile.id,
            screenName: [userProfile.screenName, Validators.required],
            gender: userProfile.gender,
            dob: userProfile.dob,
            city: userProfile.city,
            personalities: userProfile.personalities,
            interests: userProfile.interests,
            offerings: userProfile.offerings
        });
    }
    selectGender(genderChip, gender) {
        // this.prefs.genderPref = gender.value;
        genderChip.select();
    }

    selectPersonality(personalityChip: MatChip, gender) {
        personalityChip.select();
    }

    selectInterest(interestChip, interest) {
        interestChip.select();
    }
    selectOffering(offeringChip, offering) {
        offeringChip.select();
    }
}
