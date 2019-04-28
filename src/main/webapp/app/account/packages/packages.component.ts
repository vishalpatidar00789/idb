import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core';
import { UserPackagesService } from './user-packages.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { IPackages } from 'app/shared/model/packages.model';

@Component({
    selector: 'jhi-packages',
    templateUrl: './packages.html',
    styleUrls: ['packages.scss']
})
export class PackagesComponent implements OnInit {
    account: Account;
    panelOpenState = false;
    selectedPackage: string = 'GOLD';
    isFreeSelected = false;
    isGoldSelected = true;
    isDimondSelected = false;
    availPackages: any;

    constructor(private accountService: AccountService, private userPackagesService: UserPackagesService) {}

    ngOnInit() {
        console.log('packages activated');
        this.selectedPackage = 'GOLD';
        this.accountService
            .identity()
            .then(account => {
                console.log('auth done :: user logged in :: ' + JSON.stringify(account));
                if (account) {
                    this.account = account;
                } else {
                    // route it to registration
                }
            })
            .catch(() => {
                console.log('error while auth success event :: accessing account');
                this.account = undefined;
            });
        this.loadAllAvailablePackages();
    }

    isSelected(pkg) {
        if (pkg.type === 'GOLD') {
            return true;
        } else {
            return false;
        }
    }

    loadAllAvailablePackages() {
        console.log('loading all packages');
        this.userPackagesService
            .query({
                // page: this.page - 1,
                // size: this.itemsPerPage,
                // sort: this.sort()
            })
            .subscribe((res: HttpResponse<IPackages[]>) => this.onSuccess(res.body), (res: HttpErrorResponse) => this.onError(res.message));
    }

    protected onSuccess(data: IPackages[]) {
        this.availPackages = data;
        console.log('INFO:: available packages:: ' + JSON.stringify(this.availPackages));
    }

    protected onError(errorMessage: string) {
        // this.jhiAlertService.error(errorMessage, null, null);
        console.log('ERROR: in getting available packages:: ' + errorMessage);
    }

    selectPkg(pkg) {
        console.log('selected pkg :: ' + pkg.type);
        this.selectedPackage = pkg.type;
        // console.log('chip value :: ' +this.selectedPackage + 'type of :: ' + typeof chip.value);
        // if (this.selectedPackage === 'FREE') {
        //   console.log('free selected');
        //   this.isFreeSelected = true;
        //   this.isGoldSelected = false;
        //   this.isDimondSelected = false;
        // } else if (this.selectedPackage === 'GOLD') {
        //   console.log('gold selected');
        //   this.isFreeSelected = false;
        //   this.isGoldSelected = true;
        //   this.isDimondSelected = false;
        // } else if (this.selectedPackage === 'DIMOND') {
        //   console.log('dimond selected');
        //   this.isFreeSelected = false;
        //   this.isGoldSelected = false;
        //   this.isDimondSelected = true;
        // }
    }

    selectColor(pkg) {
        if (pkg.type === 'FREE') {
            return 'warn';
        } else if (pkg.type === 'GOLD') {
            return 'primary';
        } else if (pkg.type === 'DIMOND') {
            return 'accent';
        }
    }
}
