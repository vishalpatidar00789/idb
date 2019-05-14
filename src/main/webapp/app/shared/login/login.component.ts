import { Component, AfterViewInit, Renderer, ElementRef, Inject, Output, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { LoginService } from 'app/core/login/login.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EventEmitter } from 'protractor';

@Component({
    selector: 'jhi-login-modal',
    templateUrl: './login.component.html'
})
export class JhiLoginModalComponent implements OnInit, AfterViewInit {
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;
    isSignInDisabled: Boolean = false;
    isSignUpDisabled: Boolean = true;
    index = 0;
    // @Output()selectedIndexChange: EventEmitter = ;
    // @Output()selectedTabChange: EventEmitter = new EventEmitter();
    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private router: Router,
        public dialogRef: MatDialogRef<JhiLoginModalComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {
        this.credentials = {};
    }

    ngOnInit(): void {
        if (this.data && !this.data.isLogin) {
            console.log('is login:: ' + this.data.isLogin);
            this.index = 1;
        } else {
            console.log('is login:: ' + this.data.isLogin);
            this.index = 0;
        }
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    onTabChange(tab): void {
        console.log('tab changed :: ' + tab.toString() + ' index :: ' + this.index);
        this.index = 1;
    }

    loadDynamicContent() {}

    ngAfterViewInit() {
        // setTimeout(() => this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []), 0);
    }

    cancel() {
        this.credentials = {
            username: null,
            password: null,
            rememberMe: true
        };
        this.authenticationError = false;
        // this.activeModal.dismiss('cancel');
        this.dialogRef.close();
    }

    login() {
        this.loginService
            .login({
                username: this.username,
                password: this.password,
                rememberMe: this.rememberMe
            })
            .then(() => {
                this.authenticationError = false;
                // this.activeModal.dismiss('login success');
                this.dialogRef.close();
                if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                    this.router.navigate(['']);
                }

                this.eventManager.broadcast({
                    name: 'authenticationSuccess',
                    content: 'Sending Authentication Success'
                });

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                const redirect = this.stateStorageService.getUrl();
                if (redirect) {
                    this.stateStorageService.storeUrl(null);
                    this.router.navigate([redirect]);
                }
            })
            .catch(() => {
                this.authenticationError = true;
            });
    }

    register() {
        // this.activeModal.dismiss('to state register');
        // this.router.navigate(['/register']);
    }

    requestResetPassword() {
        // this.activeModal.dismiss('to state requestReset');
        this.router.navigate(['/reset', 'request']);
    }
}
