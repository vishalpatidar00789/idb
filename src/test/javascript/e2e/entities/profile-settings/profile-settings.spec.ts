/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProfileSettingsComponentsPage, ProfileSettingsDeleteDialog, ProfileSettingsUpdatePage } from './profile-settings.page-object';

const expect = chai.expect;

describe('ProfileSettings e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let profileSettingsUpdatePage: ProfileSettingsUpdatePage;
    let profileSettingsComponentsPage: ProfileSettingsComponentsPage;
    let profileSettingsDeleteDialog: ProfileSettingsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProfileSettings', async () => {
        await navBarPage.goToEntity('profile-settings');
        profileSettingsComponentsPage = new ProfileSettingsComponentsPage();
        await browser.wait(ec.visibilityOf(profileSettingsComponentsPage.title), 5000);
        expect(await profileSettingsComponentsPage.getTitle()).to.eq('Profile Settings');
    });

    it('should load create ProfileSettings page', async () => {
        await profileSettingsComponentsPage.clickOnCreateButton();
        profileSettingsUpdatePage = new ProfileSettingsUpdatePage();
        expect(await profileSettingsUpdatePage.getPageTitle()).to.eq('Create or edit a Profile Settings');
        await profileSettingsUpdatePage.cancel();
    });

    it('should create and save ProfileSettings', async () => {
        const nbButtonsBeforeCreate = await profileSettingsComponentsPage.countDeleteButtons();

        await profileSettingsComponentsPage.clickOnCreateButton();
        await promise.all([
            profileSettingsUpdatePage.setKeyInput('key'),
            profileSettingsUpdatePage.setCreatedDateInput('2000-12-31'),
            profileSettingsUpdatePage.setCreatedByInput('createdBy'),
            profileSettingsUpdatePage.setLastUpdatedDateInput('2000-12-31'),
            profileSettingsUpdatePage.setLastUpdatedByInput('lastUpdatedBy'),
            profileSettingsUpdatePage.userProfileSelectLastOption()
        ]);
        expect(await profileSettingsUpdatePage.getKeyInput()).to.eq('key');
        const selectedIsPublic = profileSettingsUpdatePage.getIsPublicInput();
        if (await selectedIsPublic.isSelected()) {
            await profileSettingsUpdatePage.getIsPublicInput().click();
            expect(await profileSettingsUpdatePage.getIsPublicInput().isSelected()).to.be.false;
        } else {
            await profileSettingsUpdatePage.getIsPublicInput().click();
            expect(await profileSettingsUpdatePage.getIsPublicInput().isSelected()).to.be.true;
        }
        expect(await profileSettingsUpdatePage.getCreatedDateInput()).to.eq('2000-12-31');
        expect(await profileSettingsUpdatePage.getCreatedByInput()).to.eq('createdBy');
        expect(await profileSettingsUpdatePage.getLastUpdatedDateInput()).to.eq('2000-12-31');
        expect(await profileSettingsUpdatePage.getLastUpdatedByInput()).to.eq('lastUpdatedBy');
        await profileSettingsUpdatePage.save();
        expect(await profileSettingsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await profileSettingsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProfileSettings', async () => {
        const nbButtonsBeforeDelete = await profileSettingsComponentsPage.countDeleteButtons();
        await profileSettingsComponentsPage.clickOnLastDeleteButton();

        profileSettingsDeleteDialog = new ProfileSettingsDeleteDialog();
        expect(await profileSettingsDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Profile Settings?');
        await profileSettingsDeleteDialog.clickOnConfirmButton();

        expect(await profileSettingsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
