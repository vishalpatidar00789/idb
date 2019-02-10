/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IDBUserComponentsPage, IDBUserDeleteDialog, IDBUserUpdatePage } from './idb-user.page-object';

const expect = chai.expect;

describe('IDBUser e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let iDBUserUpdatePage: IDBUserUpdatePage;
    let iDBUserComponentsPage: IDBUserComponentsPage;
    let iDBUserDeleteDialog: IDBUserDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load IDBUsers', async () => {
        await navBarPage.goToEntity('idb-user');
        iDBUserComponentsPage = new IDBUserComponentsPage();
        await browser.wait(ec.visibilityOf(iDBUserComponentsPage.title), 5000);
        expect(await iDBUserComponentsPage.getTitle()).to.eq('IDB Users');
    });

    it('should load create IDBUser page', async () => {
        await iDBUserComponentsPage.clickOnCreateButton();
        iDBUserUpdatePage = new IDBUserUpdatePage();
        expect(await iDBUserUpdatePage.getPageTitle()).to.eq('Create or edit a IDB User');
        await iDBUserUpdatePage.cancel();
    });

    it('should create and save IDBUsers', async () => {
        const nbButtonsBeforeCreate = await iDBUserComponentsPage.countDeleteButtons();

        await iDBUserComponentsPage.clickOnCreateButton();
        await promise.all([
            iDBUserUpdatePage.setEmailInput('email'),
            iDBUserUpdatePage.setPasswordInput('password'),
            iDBUserUpdatePage.setAccessTokenInput('accessToken'),
            iDBUserUpdatePage.setSessionTokenInput('sessionToken'),
            iDBUserUpdatePage.setLastLoginDateInput('2000-12-31'),
            iDBUserUpdatePage.setLastDeactivatedDateInput('2000-12-31'),
            iDBUserUpdatePage.userRolesSelectLastOption(),
            iDBUserUpdatePage.setVerificationMethodInput('verificationMethod'),
            iDBUserUpdatePage.setLastLogoutInput('2000-12-31'),
            iDBUserUpdatePage.setLastActivatedDateInput('2000-12-31'),
            iDBUserUpdatePage.setCreatedDateInput('2000-12-31'),
            iDBUserUpdatePage.setCreatedByInput('createdBy'),
            iDBUserUpdatePage.setLastUpdatedDateInput('2000-12-31'),
            iDBUserUpdatePage.setLastUpdatedByInput('lastUpdatedBy')
        ]);
        expect(await iDBUserUpdatePage.getEmailInput()).to.eq('email');
        expect(await iDBUserUpdatePage.getPasswordInput()).to.eq('password');
        const selectedActivated = iDBUserUpdatePage.getActivatedInput();
        if (await selectedActivated.isSelected()) {
            await iDBUserUpdatePage.getActivatedInput().click();
            expect(await iDBUserUpdatePage.getActivatedInput().isSelected()).to.be.false;
        } else {
            await iDBUserUpdatePage.getActivatedInput().click();
            expect(await iDBUserUpdatePage.getActivatedInput().isSelected()).to.be.true;
        }
        expect(await iDBUserUpdatePage.getAccessTokenInput()).to.eq('accessToken');
        expect(await iDBUserUpdatePage.getSessionTokenInput()).to.eq('sessionToken');
        expect(await iDBUserUpdatePage.getLastLoginDateInput()).to.eq('2000-12-31');
        expect(await iDBUserUpdatePage.getLastDeactivatedDateInput()).to.eq('2000-12-31');
        const selectedVerified = iDBUserUpdatePage.getVerifiedInput();
        if (await selectedVerified.isSelected()) {
            await iDBUserUpdatePage.getVerifiedInput().click();
            expect(await iDBUserUpdatePage.getVerifiedInput().isSelected()).to.be.false;
        } else {
            await iDBUserUpdatePage.getVerifiedInput().click();
            expect(await iDBUserUpdatePage.getVerifiedInput().isSelected()).to.be.true;
        }
        expect(await iDBUserUpdatePage.getVerificationMethodInput()).to.eq('verificationMethod');
        const selectedIsReportedScam = iDBUserUpdatePage.getIsReportedScamInput();
        if (await selectedIsReportedScam.isSelected()) {
            await iDBUserUpdatePage.getIsReportedScamInput().click();
            expect(await iDBUserUpdatePage.getIsReportedScamInput().isSelected()).to.be.false;
        } else {
            await iDBUserUpdatePage.getIsReportedScamInput().click();
            expect(await iDBUserUpdatePage.getIsReportedScamInput().isSelected()).to.be.true;
        }
        expect(await iDBUserUpdatePage.getLastLogoutInput()).to.eq('2000-12-31');
        expect(await iDBUserUpdatePage.getLastActivatedDateInput()).to.eq('2000-12-31');
        expect(await iDBUserUpdatePage.getCreatedDateInput()).to.eq('2000-12-31');
        expect(await iDBUserUpdatePage.getCreatedByInput()).to.eq('createdBy');
        expect(await iDBUserUpdatePage.getLastUpdatedDateInput()).to.eq('2000-12-31');
        expect(await iDBUserUpdatePage.getLastUpdatedByInput()).to.eq('lastUpdatedBy');
        await iDBUserUpdatePage.save();
        expect(await iDBUserUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await iDBUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last IDBUser', async () => {
        const nbButtonsBeforeDelete = await iDBUserComponentsPage.countDeleteButtons();
        await iDBUserComponentsPage.clickOnLastDeleteButton();

        iDBUserDeleteDialog = new IDBUserDeleteDialog();
        expect(await iDBUserDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this IDB User?');
        await iDBUserDeleteDialog.clickOnConfirmButton();

        expect(await iDBUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
