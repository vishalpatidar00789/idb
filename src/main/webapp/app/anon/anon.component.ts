import { Component, OnInit } from '@angular/core';
import { LoginModalService, AccountService, Account } from 'app/core';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-anon',
    templateUrl: './anon.component.html'
    // styleUrls: ['anon.scss']
})
export class AnonComponent implements OnInit {
    account: Account;
    listMatches: any;
    constructor(private accountService: AccountService, private router: Router) {}

    ngOnInit() {
        // check for whether the current user is logged in or not
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });
        if (!this.account) {
            this.account = undefined;
        }
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
}
