import { element, by, ElementFinder } from 'protractor';

export class PaymentsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-payments div table .btn-danger'));
    title = element.all(by.css('jhi-payments div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class PaymentsUpdatePage {
    pageTitle = element(by.id('jhi-payments-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    vendorInput = element(by.id('field_vendor'));
    statusSelect = element(by.id('field_status'));
    paymentValueInput = element(by.id('field_paymentValue'));
    initiatedDateInput = element(by.id('field_initiatedDate'));
    confirmDateInput = element(by.id('field_confirmDate'));
    activatedInput = element(by.id('field_activated'));
    createdDateInput = element(by.id('field_createdDate'));
    createdByInput = element(by.id('field_createdBy'));
    lastUpdatedDateInput = element(by.id('field_lastUpdatedDate'));
    lastUpdatedByInput = element(by.id('field_lastUpdatedBy'));
    userAccountSelect = element(by.id('field_userAccount'));
    appliedPackageSelect = element(by.id('field_appliedPackage'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setVendorInput(vendor) {
        await this.vendorInput.sendKeys(vendor);
    }

    async getVendorInput() {
        return this.vendorInput.getAttribute('value');
    }

    async setStatusSelect(status) {
        await this.statusSelect.sendKeys(status);
    }

    async getStatusSelect() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    async statusSelectLastOption() {
        await this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setPaymentValueInput(paymentValue) {
        await this.paymentValueInput.sendKeys(paymentValue);
    }

    async getPaymentValueInput() {
        return this.paymentValueInput.getAttribute('value');
    }

    async setInitiatedDateInput(initiatedDate) {
        await this.initiatedDateInput.sendKeys(initiatedDate);
    }

    async getInitiatedDateInput() {
        return this.initiatedDateInput.getAttribute('value');
    }

    async setConfirmDateInput(confirmDate) {
        await this.confirmDateInput.sendKeys(confirmDate);
    }

    async getConfirmDateInput() {
        return this.confirmDateInput.getAttribute('value');
    }

    getActivatedInput() {
        return this.activatedInput;
    }
    async setCreatedDateInput(createdDate) {
        await this.createdDateInput.sendKeys(createdDate);
    }

    async getCreatedDateInput() {
        return this.createdDateInput.getAttribute('value');
    }

    async setCreatedByInput(createdBy) {
        await this.createdByInput.sendKeys(createdBy);
    }

    async getCreatedByInput() {
        return this.createdByInput.getAttribute('value');
    }

    async setLastUpdatedDateInput(lastUpdatedDate) {
        await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
    }

    async getLastUpdatedDateInput() {
        return this.lastUpdatedDateInput.getAttribute('value');
    }

    async setLastUpdatedByInput(lastUpdatedBy) {
        await this.lastUpdatedByInput.sendKeys(lastUpdatedBy);
    }

    async getLastUpdatedByInput() {
        return this.lastUpdatedByInput.getAttribute('value');
    }

    async userAccountSelectLastOption() {
        await this.userAccountSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userAccountSelectOption(option) {
        await this.userAccountSelect.sendKeys(option);
    }

    getUserAccountSelect(): ElementFinder {
        return this.userAccountSelect;
    }

    async getUserAccountSelectedOption() {
        return this.userAccountSelect.element(by.css('option:checked')).getText();
    }

    async appliedPackageSelectLastOption() {
        await this.appliedPackageSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async appliedPackageSelectOption(option) {
        await this.appliedPackageSelect.sendKeys(option);
    }

    getAppliedPackageSelect(): ElementFinder {
        return this.appliedPackageSelect;
    }

    async getAppliedPackageSelectedOption() {
        return this.appliedPackageSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class PaymentsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-payments-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-payments'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
