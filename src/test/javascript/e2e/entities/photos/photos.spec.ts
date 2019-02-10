/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PhotosComponentsPage, PhotosDeleteDialog, PhotosUpdatePage } from './photos.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Photos e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let photosUpdatePage: PhotosUpdatePage;
    let photosComponentsPage: PhotosComponentsPage;
    let photosDeleteDialog: PhotosDeleteDialog;
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

    it('should load Photos', async () => {
        await navBarPage.goToEntity('photos');
        photosComponentsPage = new PhotosComponentsPage();
        await browser.wait(ec.visibilityOf(photosComponentsPage.title), 5000);
        expect(await photosComponentsPage.getTitle()).to.eq('Photos');
    });

    it('should load create Photos page', async () => {
        await photosComponentsPage.clickOnCreateButton();
        photosUpdatePage = new PhotosUpdatePage();
        expect(await photosUpdatePage.getPageTitle()).to.eq('Create or edit a Photos');
        await photosUpdatePage.cancel();
    });

    it('should create and save Photos', async () => {
        const nbButtonsBeforeCreate = await photosComponentsPage.countDeleteButtons();

        await photosComponentsPage.clickOnCreateButton();
        await promise.all([
            photosUpdatePage.setImageInput(absolutePath),
            photosUpdatePage.setCreatedDateInput('2000-12-31'),
            photosUpdatePage.setCreatedByInput('createdBy'),
            photosUpdatePage.setLastUpdatedDateInput('2000-12-31'),
            photosUpdatePage.setLastUpdatedByInput('lastUpdatedBy'),
            photosUpdatePage.userProfileSelectLastOption()
        ]);
        expect(await photosUpdatePage.getImageInput()).to.endsWith(fileNameToUpload);
        expect(await photosUpdatePage.getCreatedDateInput()).to.eq('2000-12-31');
        expect(await photosUpdatePage.getCreatedByInput()).to.eq('createdBy');
        expect(await photosUpdatePage.getLastUpdatedDateInput()).to.eq('2000-12-31');
        expect(await photosUpdatePage.getLastUpdatedByInput()).to.eq('lastUpdatedBy');
        await photosUpdatePage.save();
        expect(await photosUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await photosComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Photos', async () => {
        const nbButtonsBeforeDelete = await photosComponentsPage.countDeleteButtons();
        await photosComponentsPage.clickOnLastDeleteButton();

        photosDeleteDialog = new PhotosDeleteDialog();
        expect(await photosDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Photos?');
        await photosDeleteDialog.clickOnConfirmButton();

        expect(await photosComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
