/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ChaptersComponentsPage, ChaptersDeleteDialog, ChaptersUpdatePage } from './chapters.page-object';

const expect = chai.expect;

describe('Chapters e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let chaptersUpdatePage: ChaptersUpdatePage;
    let chaptersComponentsPage: ChaptersComponentsPage;
    let chaptersDeleteDialog: ChaptersDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Chapters', async () => {
        await navBarPage.goToEntity('chapters');
        chaptersComponentsPage = new ChaptersComponentsPage();
        await browser.wait(ec.visibilityOf(chaptersComponentsPage.title), 5000);
        expect(await chaptersComponentsPage.getTitle()).to.eq('Chapters');
    });

    it('should load create Chapters page', async () => {
        await chaptersComponentsPage.clickOnCreateButton();
        chaptersUpdatePage = new ChaptersUpdatePage();
        expect(await chaptersUpdatePage.getPageTitle()).to.eq('Create or edit a Chapters');
        await chaptersUpdatePage.cancel();
    });

    it('should create and save Chapters', async () => {
        const nbButtonsBeforeCreate = await chaptersComponentsPage.countDeleteButtons();

        await chaptersComponentsPage.clickOnCreateButton();
        await promise.all([
            chaptersUpdatePage.statusSelectLastOption(),
            chaptersUpdatePage.setActivatedInput('activated'),
            chaptersUpdatePage.setCreatedDateInput('2000-12-31'),
            chaptersUpdatePage.setCreatedByInput('createdBy'),
            chaptersUpdatePage.setLastUpdatedDateInput('2000-12-31'),
            chaptersUpdatePage.setLastUpdatedByInput('lastUpdatedBy'),
            chaptersUpdatePage.initiatorSelectLastOption(),
            chaptersUpdatePage.partnerSelectLastOption()
        ]);
        expect(await chaptersUpdatePage.getActivatedInput()).to.eq('activated');
        expect(await chaptersUpdatePage.getCreatedDateInput()).to.eq('2000-12-31');
        expect(await chaptersUpdatePage.getCreatedByInput()).to.eq('createdBy');
        expect(await chaptersUpdatePage.getLastUpdatedDateInput()).to.eq('2000-12-31');
        expect(await chaptersUpdatePage.getLastUpdatedByInput()).to.eq('lastUpdatedBy');
        await chaptersUpdatePage.save();
        expect(await chaptersUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await chaptersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Chapters', async () => {
        const nbButtonsBeforeDelete = await chaptersComponentsPage.countDeleteButtons();
        await chaptersComponentsPage.clickOnLastDeleteButton();

        chaptersDeleteDialog = new ChaptersDeleteDialog();
        expect(await chaptersDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Chapters?');
        await chaptersDeleteDialog.clickOnConfirmButton();

        expect(await chaptersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
