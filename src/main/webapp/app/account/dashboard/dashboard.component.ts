import { OnInit, Component, ChangeDetectorRef } from '@angular/core';
import { AccountService, Preferences } from 'app/core';
import { Router } from '@angular/router';
import { MediaMatcher } from '@angular/cdk/layout';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['dashboard.scss']
})
export class DashboardComponent implements OnInit {
    private preferences: Preferences;
    private listMatches: any;
    private account: Account;
    mobileQuery: MediaQueryList;
    private _mobileQueryListener: () => void;
    constructor(private accountService: AccountService, private router: Router, changeDetectorRef: ChangeDetectorRef, media: MediaMatcher) {
        this.mobileQuery = media.matchMedia('(max-width: 600px)');
        this._mobileQueryListener = () => changeDetectorRef.detectChanges();
        this.mobileQuery.addListener(this._mobileQueryListener);
    }

    ngOnInit(): void {
        // throw new Error("Method not implemented.");

        this.accountService
            .identity()
            .then(account => {
                console.log('auth done :: user logged in :: ' + JSON.stringify(account));
                if (account) {
                    this.preferences = this.accountService.getPreferences();
                    this.account = account;
                    // call list matches by passing preferences

                    this.listMatches = [
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
                } else {
                    // redirect to login
                }
            })
            .catch(() => {
                console.log('error while auth success event :: accessing account');
                // redirect to login
            });
    }
}
