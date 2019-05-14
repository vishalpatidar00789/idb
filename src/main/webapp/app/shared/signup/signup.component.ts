import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared';
import { LoginModalService, LoginService, AccountService } from 'app/core';
import { Signup } from './signup.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'jhi-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['signup.scss']
})
export class SignupComponent implements OnInit, AfterViewInit {
    // confirmPassword: string;
    account: Account;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: Boolean;
    modalRef: NgbModalRef;
    authenticationError: Boolean = false;
    registerForm: FormGroup;

    isLinear = false;
    index = 0;
    regTab = false;
    prefTab = false;
    selPkgTab = true;
    payTab = true;

    username: string;
    password: string;

    regOrLogin: boolean;
    selectedForm: string;

    constructor(
        private loginModalService: LoginModalService,
        private registerService: Signup,
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private accountService: AccountService
    ) {}

    ngOnInit() {
        console.log('sign init');
        this.regOrLogin = true;
        this.selectedForm = 'REGISTER';
        this.registerAuthenticationSuccess();
        this.accountService
            .identity()
            .then(account => {
                console.log('auth done :: user logged in :: ' + JSON.stringify(account));
                if (account) {
                    this.account = account;
                    this.regTab = true;
                    this.selPkgTab = false;
                    this.payTab = true;
                    this.index = 1;
                } else {
                    this.regTab = false;
                    this.selPkgTab = true;
                    this.payTab = true;
                    this.index = 0;
                }
            })
            .catch(() => {
                console.log('error while auth success event :: accessing account');
                this.account = undefined;
                this.regTab = false;
                this.prefTab = false;
                this.selPkgTab = true;
                this.payTab = true;
                this.index = 0;
            });

        this.success = false;
        this.registerForm = new FormGroup({
            email: new FormControl('', [Validators.required, Validators.maxLength(60)]),
            password: new FormControl('', [Validators.required, Validators.maxLength(60)]),
            confirmPassword: new FormControl('', [Validators.required, Validators.maxLength(60)])
        });
    }

    public hasError = (controlName: string, errorName: string) => {
        return this.registerForm.controls[controlName].hasError(errorName);
    };

    ngAfterViewInit() {
        // this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService
                .identity()
                .then(account => {
                    console.log('auth done :: user logged in :: ' + JSON.stringify(account));
                    if (account) {
                        this.account = account;
                        this.regTab = true;
                        this.prefTab = false;
                        this.selPkgTab = true;
                        this.payTab = true;
                        this.index = 1;
                    } else {
                        this.regTab = false;
                        this.prefTab = true;
                        this.selPkgTab = true;
                        this.payTab = true;
                        this.index = 0;
                    }
                })
                .catch(() => {
                    console.log('error while auth success event :: accessing account');
                    this.account = undefined;
                    this.regTab = false;
                    this.prefTab = true;
                    this.selPkgTab = true;
                    this.payTab = true;
                    this.index = 0;
                });
        });
    }

    onChangeOfGender(gender) {
        console.log('selected val ' + JSON.stringify(gender.value));
        gender.checked = !gender.checked;
        // this.filters.gender = gender.value;
    }

    selectRegOrLogin(chip) {
        // this.filters.gender = gender.value;
        console.log('selected val ' + JSON.stringify(chip.value));
        if (chip && chip.value === 'REGISTER') {
            this.regOrLogin = true;
            this.selectedForm = 'REGISTER';
        } else {
            this.regOrLogin = false;
            this.selectedForm = 'LOGIN';
        }
        chip.select();
    }

    register(registerForm: any) {
        this.registerAccount = registerForm;
        // console.log('user :: ' + this.registerAccount);
        console.log('user :: ' + JSON.stringify(this.registerAccount));
        if (this.registerAccount.password !== this.registerAccount.confirmPassword) {
            console.log('password doesnt match');
            this.doNotMatch = 'ERROR';
        } else {
            console.log('password matched');
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.registerAccount.langKey = 'en';
            this.registerService.save(this.registerAccount).subscribe(
                () => {
                    this.success = true;
                    console.log('user created');
                    this.username = this.registerAccount.username;
                    this.password = this.registerAccount.password;
                    this.login();
                },
                response => this.processError(response)
            );
        }
    }

    openLogin() {
        this.modalRef = this.loginModalService.open(true);
    }

    private processError(response: HttpErrorResponse) {
        this.success = null;
        console.log('error ' + JSON.stringify(response));
        if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }

    login() {
        this.loginService
            .login({
                username: this.username,
                password: this.password
                // rememberMe: this.rememberMe
            })
            .then(() => {
                this.authenticationError = false;
                // this.activeModal.dismiss('login success');
                // if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                //     this.router.navigate(['']);
                // }

                this.eventManager.broadcast({
                    name: 'authenticationSuccess',
                    content: 'Sending Authentication Success'
                });

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                // const redirect = this.stateStorageService.getUrl();
                // if (redirect) {
                //     this.stateStorageService.storeUrl(null);
                //     this.router.navigate([redirect]);
                // }
                console.log('login success');
            })
            .catch(() => {
                this.authenticationError = true;
                console.log('errors in login');
            });
    }
}
