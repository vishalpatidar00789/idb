import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { LoginModalService, AccountService, Account } from 'app/core';
import { Router } from '@angular/router';
import { NgxGalleryOptions, NgxGalleryImage, NgxGalleryAnimation } from 'ngx-gallery';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    employeeForm:  FormGroup;
    galleryOptions: NgxGalleryOptions[];
    galleryImages: NgxGalleryImage[];
    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private fb: FormBuilder,
        private router: Router) {}

    ngOnInit() {
      this.galleryOptions = [
        {
          width: '600px',
          height: '400px',
          thumbnailsColumns: 4,
          imageDescription : true,
          thumbnails : false,
          imageSwipe : true,
          imageAnimation: NgxGalleryAnimation.Slide
      },
      // max-width 800
      {
          breakpoint: 500,
          width: '300px',
          height: '300px',
          thumbnailsColumns: 3,
          imagePercent: 80,
          thumbnailsPercent: 20,
          thumbnailsMargin: 20,
          thumbnailMargin: 20
      },
        // max-width 400
        {
            breakpoint: 300,
            width: '100%',
            height: '200px',
            thumbnailsColumns: 2,
            preview: false
        }
    ];

    this.galleryImages = [
        {
            small: 'https://404store.com/2018/03/29/Beautiful-Girls-Pictures-With-Nature4.jpg',
            medium: 'https://404store.com/2018/03/29/Beautiful-Girls-Pictures-With-Nature4.jpg',
            big: 'https://404store.com/2018/03/29/Beautiful-Girls-Pictures-With-Nature4.jpg'
        },
        {
          small: 'https://images.hindi.news18.com/ibnkhabar/uploads/459x306/jpg/2018/02/p5-1.jpg',
          medium: 'https://images.hindi.news18.com/ibnkhabar/uploads/459x306/jpg/2018/02/p5-1.jpg',
          big: 'https://images.hindi.news18.com/ibnkhabar/uploads/459x306/jpg/2018/02/p5-1.jpg'
         },
         {
          small: 'https://cdn.pixabay.com/photo/2013/07/21/13/00/rose-165819_960_720.jpg',
          medium: 'https://cdn.pixabay.com/photo/2013/07/21/13/00/rose-165819_960_720.jpg',
          big: 'https://cdn.pixabay.com/photo/2013/07/21/13/00/rose-165819_960_720.jpg'
         }
    ];
    this.galleryImages[0]['description'] = "setting new description";
    this.galleryImages = this.galleryImages.slice(0, this.galleryImages.length);

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
        this.router.navigate(['/listMatches']);
      }
}
