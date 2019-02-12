import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { LoginModalService, AccountService, Account } from 'app/core';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    employeeForm:  FormGroup;

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private fb: FormBuilder) {}

    ngOnInit() {
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

        this.employeeForm = this.fb.group({
            gender : ['', Validators.required],
            lfGender : ['', Validators.required],
            age : ['', Validators.required],
            country : ['', Validators.required],
            state : ['', Validators.required],
            city : ['', Validators.required]
          });

          this.employeeForm.valueChanges.subscribe(data => {
            this.logValidationErrors(this.employeeForm);
          });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logValidationErrors(group: FormGroup = this.employeeForm): void {
        Object.keys(group.controls).forEach((key: string) => {
          const abstractControl = group.get(key);

          if(abstractControl instanceof FormGroup) {
            this.logValidationErrors(abstractControl);
          } else {
            this.formErrors[key] = '';
            if(abstractControl && !abstractControl.valid && (abstractControl.touched || abstractControl.dirty)) {
              const messages  = this.validationMessages[key];
              console.log(messages);
              for(const errorKey in abstractControl.errors) {
                if(errorKey) {
                  this.formErrors[key] += messages[errorKey] + ' ';
                }
              }
            }
          }
        });
    }

    validationMessages = {
        'gender' : {
          'required' : 'Gender is required',
        },
        'lfGender' : {
            'required' : 'Looking for gender is required',
        },
        'age' : {
        'required' : 'Age is required',
        },
        'country' : {
            'required' : 'Country is required',
        },
        'state' : {
            'required' : 'State is required',
        },
        'city' : {
            'required' : 'City is required',
        }

    };

    formErrors = {
      'gender' : '',
      'lfGender' : '',
      'age' : '',
      'country' : '',
      'state' : '',
      'city' : ''
    };

    onSubmit(): void {
        console.log(this.employeeForm.value);
      }
}
