/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UserAccountComponentsPage, UserAccountDeleteDialog, UserAccountUpdatePage } from './user-account.page-object';

const expect = chai.expect;

describe('UserAccount e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let userAccountUpdatePage: UserAccountUpdatePage;
    let userAccountComponentsPage: UserAccountComponentsPage;
    let userAccountDeleteDialog: UserAccountDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UserAccounts', async () => {
        await navBarPage.goToEntity('user-account');
        userAccountComponentsPage = new UserAccountComponentsPage();
        await browser.wait(ec.visibilityOf(userAccountComponentsPage.title), 5000);
        expect(await userAccountComponentsPage.getTitle()).to.eq('User Accounts');
    });

    it('should load create UserAccount page', async () => {
        await userAccountComponentsPage.clickOnCreateButton();
        userAccountUpdatePage = new UserAccountUpdatePage();
        expect(await userAccountUpdatePage.getPageTitle()).to.eq('Create or edit a User Account');
        await userAccountUpdatePage.cancel();
    });

    it('should create and save UserAccounts', async () => {
        const nbButtonsBeforeCreate = await userAccountComponentsPage.countDeleteButtons();

        await userAccountComponentsPage.clickOnCreateButton();
        await promise.all([
            userAccountUpdatePage.setRemainingChaptersInput('5'),
            userAccountUpdatePage.setCurrPackageStartDateInput('2000-12-31'),
            userAccountUpdatePage.setCurrPackageEndDateInput('2000-12-31'),
            userAccountUpdatePage.setUserDiscountInput('5'),
            userAccountUpdatePage.setActivatedInput('activated'),
            userAccountUpdatePage.accountTypeSelectLastOption(),
            userAccountUpdatePage.setPerDayChapterLimitInput('5'),
            userAccountUpdatePage.setCreatedDateInput('2000-12-31'),
            userAccountUpdatePage.setCreatedByInput('createdBy'),
            userAccountUpdatePage.setLastUpdatedDateInput('2000-12-31'),
            userAccountUpdatePage.setLastUpdatedByInput('lastUpdatedBy'),
            userAccountUpdatePage.userSelectLastOption(),
            userAccountUpdatePage.currentPackageSelectLastOption()
        ]);
        expect(await userAccountUpdatePage.getRemainingChaptersInput()).to.eq('5');
        expect(await userAccountUpdatePage.getCurrPackageStartDateInput()).to.eq('2000-12-31');
        expect(await userAccountUpdatePage.getCurrPackageEndDateInput()).to.eq('2000-12-31');
        expect(await userAccountUpdatePage.getUserDiscountInput()).to.eq('5');
        expect(await userAccountUpdatePage.getActivatedInput()).to.eq('activated');
        expect(await userAccountUpdatePage.getPerDayChapterLimitInput()).to.eq('5');
        expect(await userAccountUpdatePage.getCreatedDateInput()).to.eq('2000-12-31');
        expect(await userAccountUpdatePage.getCreatedByInput()).to.eq('createdBy');
        expect(await userAccountUpdatePage.getLastUpdatedDateInput()).to.eq('2000-12-31');
        expect(await userAccountUpdatePage.getLastUpdatedByInput()).to.eq('lastUpdatedBy');
        await userAccountUpdatePage.save();
        expect(await userAccountUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await userAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UserAccount', async () => {
        const nbButtonsBeforeDelete = await userAccountComponentsPage.countDeleteButtons();
        await userAccountComponentsPage.clickOnLastDeleteButton();

        userAccountDeleteDialog = new UserAccountDeleteDialog();
        expect(await userAccountDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this User Account?');
        await userAccountDeleteDialog.clickOnConfirmButton();

        expect(await userAccountComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
