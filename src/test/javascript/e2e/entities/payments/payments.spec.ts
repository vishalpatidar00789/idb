/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PaymentsComponentsPage, PaymentsDeleteDialog, PaymentsUpdatePage } from './payments.page-object';

const expect = chai.expect;

describe('Payments e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let paymentsUpdatePage: PaymentsUpdatePage;
    let paymentsComponentsPage: PaymentsComponentsPage;
    let paymentsDeleteDialog: PaymentsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Payments', async () => {
        await navBarPage.goToEntity('payments');
        paymentsComponentsPage = new PaymentsComponentsPage();
        await browser.wait(ec.visibilityOf(paymentsComponentsPage.title), 5000);
        expect(await paymentsComponentsPage.getTitle()).to.eq('Payments');
    });

    it('should load create Payments page', async () => {
        await paymentsComponentsPage.clickOnCreateButton();
        paymentsUpdatePage = new PaymentsUpdatePage();
        expect(await paymentsUpdatePage.getPageTitle()).to.eq('Create or edit a Payments');
        await paymentsUpdatePage.cancel();
    });

    it('should create and save Payments', async () => {
        const nbButtonsBeforeCreate = await paymentsComponentsPage.countDeleteButtons();

        await paymentsComponentsPage.clickOnCreateButton();
        await promise.all([
            paymentsUpdatePage.setVendorInput('vendor'),
            paymentsUpdatePage.statusSelectLastOption(),
            paymentsUpdatePage.setPaymentValueInput('5'),
            paymentsUpdatePage.setInitiatedDateInput('2000-12-31'),
            paymentsUpdatePage.setConfirmDateInput('2000-12-31'),
            paymentsUpdatePage.setCreatedDateInput('2000-12-31'),
            paymentsUpdatePage.setCreatedByInput('createdBy'),
            paymentsUpdatePage.setLastUpdatedDateInput('2000-12-31'),
            paymentsUpdatePage.setLastUpdatedByInput('lastUpdatedBy'),
            paymentsUpdatePage.userAccountSelectLastOption(),
            paymentsUpdatePage.appliedPackageSelectLastOption()
        ]);
        expect(await paymentsUpdatePage.getVendorInput()).to.eq('vendor');
        expect(await paymentsUpdatePage.getPaymentValueInput()).to.eq('5');
        expect(await paymentsUpdatePage.getInitiatedDateInput()).to.eq('2000-12-31');
        expect(await paymentsUpdatePage.getConfirmDateInput()).to.eq('2000-12-31');
        const selectedActivated = paymentsUpdatePage.getActivatedInput();
        if (await selectedActivated.isSelected()) {
            await paymentsUpdatePage.getActivatedInput().click();
            expect(await paymentsUpdatePage.getActivatedInput().isSelected()).to.be.false;
        } else {
            await paymentsUpdatePage.getActivatedInput().click();
            expect(await paymentsUpdatePage.getActivatedInput().isSelected()).to.be.true;
        }
        expect(await paymentsUpdatePage.getCreatedDateInput()).to.eq('2000-12-31');
        expect(await paymentsUpdatePage.getCreatedByInput()).to.eq('createdBy');
        expect(await paymentsUpdatePage.getLastUpdatedDateInput()).to.eq('2000-12-31');
        expect(await paymentsUpdatePage.getLastUpdatedByInput()).to.eq('lastUpdatedBy');
        await paymentsUpdatePage.save();
        expect(await paymentsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await paymentsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Payments', async () => {
        const nbButtonsBeforeDelete = await paymentsComponentsPage.countDeleteButtons();
        await paymentsComponentsPage.clickOnLastDeleteButton();

        paymentsDeleteDialog = new PaymentsDeleteDialog();
        expect(await paymentsDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Payments?');
        await paymentsDeleteDialog.clickOnConfirmButton();

        expect(await paymentsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
