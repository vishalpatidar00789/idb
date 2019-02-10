/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PackagesComponentsPage, PackagesDeleteDialog, PackagesUpdatePage } from './packages.page-object';

const expect = chai.expect;

describe('Packages e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let packagesUpdatePage: PackagesUpdatePage;
    let packagesComponentsPage: PackagesComponentsPage;
    let packagesDeleteDialog: PackagesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Packages', async () => {
        await navBarPage.goToEntity('packages');
        packagesComponentsPage = new PackagesComponentsPage();
        await browser.wait(ec.visibilityOf(packagesComponentsPage.title), 5000);
        expect(await packagesComponentsPage.getTitle()).to.eq('Packages');
    });

    it('should load create Packages page', async () => {
        await packagesComponentsPage.clickOnCreateButton();
        packagesUpdatePage = new PackagesUpdatePage();
        expect(await packagesUpdatePage.getPageTitle()).to.eq('Create or edit a Packages');
        await packagesUpdatePage.cancel();
    });

    it('should create and save Packages', async () => {
        const nbButtonsBeforeCreate = await packagesComponentsPage.countDeleteButtons();

        await packagesComponentsPage.clickOnCreateButton();
        await promise.all([
            packagesUpdatePage.setTitleInput('title'),
            packagesUpdatePage.setPriceInput('5'),
            packagesUpdatePage.setTypeInput('type'),
            packagesUpdatePage.setExpiryInput('5'),
            packagesUpdatePage.setTotalChaptersInput('5'),
            packagesUpdatePage.setDiscountInput('5'),
            packagesUpdatePage.setActivatedInput('activated'),
            packagesUpdatePage.setCreatedDateInput('2000-12-31'),
            packagesUpdatePage.setCreatedByInput('createdBy'),
            packagesUpdatePage.setLastUpdatedDateInput('2000-12-31'),
            packagesUpdatePage.setLastUpdatedByInput('lastUpdatedBy')
        ]);
        expect(await packagesUpdatePage.getTitleInput()).to.eq('title');
        expect(await packagesUpdatePage.getPriceInput()).to.eq('5');
        expect(await packagesUpdatePage.getTypeInput()).to.eq('type');
        expect(await packagesUpdatePage.getExpiryInput()).to.eq('5');
        expect(await packagesUpdatePage.getTotalChaptersInput()).to.eq('5');
        expect(await packagesUpdatePage.getDiscountInput()).to.eq('5');
        expect(await packagesUpdatePage.getActivatedInput()).to.eq('activated');
        expect(await packagesUpdatePage.getCreatedDateInput()).to.eq('2000-12-31');
        expect(await packagesUpdatePage.getCreatedByInput()).to.eq('createdBy');
        expect(await packagesUpdatePage.getLastUpdatedDateInput()).to.eq('2000-12-31');
        expect(await packagesUpdatePage.getLastUpdatedByInput()).to.eq('lastUpdatedBy');
        await packagesUpdatePage.save();
        expect(await packagesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await packagesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Packages', async () => {
        const nbButtonsBeforeDelete = await packagesComponentsPage.countDeleteButtons();
        await packagesComponentsPage.clickOnLastDeleteButton();

        packagesDeleteDialog = new PackagesDeleteDialog();
        expect(await packagesDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Packages?');
        await packagesDeleteDialog.clickOnConfirmButton();

        expect(await packagesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
