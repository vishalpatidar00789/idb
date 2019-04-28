import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core';

@Component({
    selector: 'jhi-account',
    templateUrl: './account.html',
    styleUrls: ['account.scss']
})
export class AccountComponent implements OnInit {
    account: Account;
    constructor(private accountService: AccountService) {}
    ngOnInit() {
        this.accountService
            .identity()
            .then(account => {
                console.log('auth done :: user logged in :: ' + JSON.stringify(account));
                if (account) {
                    this.account = account;
                } else {
                }
            })
            .catch(() => {
                console.log('error while auth success event :: accessing account');
                this.account = undefined;
            });
    }
}
