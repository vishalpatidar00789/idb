/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UserProfileComponentsPage, UserProfileDeleteDialog, UserProfileUpdatePage } from './user-profile.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('UserProfile e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let userProfileUpdatePage: UserProfileUpdatePage;
    let userProfileComponentsPage: UserProfileComponentsPage;
    let userProfileDeleteDialog: UserProfileDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UserProfiles', async () => {
        await navBarPage.goToEntity('user-profile');
        userProfileComponentsPage = new UserProfileComponentsPage();
        await browser.wait(ec.visibilityOf(userProfileComponentsPage.title), 5000);
        expect(await userProfileComponentsPage.getTitle()).to.eq('User Profiles');
    });

    it('should load create UserProfile page', async () => {
        await userProfileComponentsPage.clickOnCreateButton();
        userProfileUpdatePage = new UserProfileUpdatePage();
        expect(await userProfileUpdatePage.getPageTitle()).to.eq('Create or edit a User Profile');
        await userProfileUpdatePage.cancel();
    });

    it('should create and save UserProfiles', async () => {
        const nbButtonsBeforeCreate = await userProfileComponentsPage.countDeleteButtons();

        await userProfileComponentsPage.clickOnCreateButton();
        await promise.all([
            userProfileUpdatePage.setScreenNameInput('screenName'),
            userProfileUpdatePage.setProfilePicInput(absolutePath),
            userProfileUpdatePage.setGenderInput('gender'),
            userProfileUpdatePage.setDobInput('dob'),
            userProfileUpdatePage.setAgeInput('5'),
            userProfileUpdatePage.setCountryInput('country'),
            userProfileUpdatePage.setStateInput('state'),
            userProfileUpdatePage.setCityInput('city'),
            userProfileUpdatePage.setPincodeInput('pincode'),
            userProfileUpdatePage.personalitySelectLastOption(),
            userProfileUpdatePage.interestsSelectLastOption(),
            userProfileUpdatePage.offersingsSelectLastOption(),
            userProfileUpdatePage.statusSelectLastOption(),
            userProfileUpdatePage.setCreatedDateInput('2000-12-31'),
            userProfileUpdatePage.setCreatedByInput('createdBy'),
            userProfileUpdatePage.setLastUpdatedDateInput('2000-12-31'),
            userProfileUpdatePage.setLastUpdatedByInput('lastUpdatedBy'),
            userProfileUpdatePage.userSelectLastOption()
        ]);
        expect(await userProfileUpdatePage.getScreenNameInput()).to.eq('screenName');
        expect(await userProfileUpdatePage.getProfilePicInput()).to.endsWith(fileNameToUpload);
        expect(await userProfileUpdatePage.getGenderInput()).to.eq('gender');
        expect(await userProfileUpdatePage.getDobInput()).to.eq('dob');
        expect(await userProfileUpdatePage.getAgeInput()).to.eq('5');
        expect(await userProfileUpdatePage.getCountryInput()).to.eq('country');
        expect(await userProfileUpdatePage.getStateInput()).to.eq('state');
        expect(await userProfileUpdatePage.getCityInput()).to.eq('city');
        expect(await userProfileUpdatePage.getPincodeInput()).to.eq('pincode');
        expect(await userProfileUpdatePage.getCreatedDateInput()).to.eq('2000-12-31');
        expect(await userProfileUpdatePage.getCreatedByInput()).to.eq('createdBy');
        expect(await userProfileUpdatePage.getLastUpdatedDateInput()).to.eq('2000-12-31');
        expect(await userProfileUpdatePage.getLastUpdatedByInput()).to.eq('lastUpdatedBy');
        await userProfileUpdatePage.save();
        expect(await userProfileUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await userProfileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UserProfile', async () => {
        const nbButtonsBeforeDelete = await userProfileComponentsPage.countDeleteButtons();
        await userProfileComponentsPage.clickOnLastDeleteButton();

        userProfileDeleteDialog = new UserProfileDeleteDialog();
        expect(await userProfileDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this User Profile?');
        await userProfileDeleteDialog.clickOnConfirmButton();

        expect(await userProfileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
