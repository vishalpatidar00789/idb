import { element, by, ElementFinder } from 'protractor';

export class PackagesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-packages div table .btn-danger'));
    title = element.all(by.css('jhi-packages div h2#page-heading span')).first();

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

export class PackagesUpdatePage {
    pageTitle = element(by.id('jhi-packages-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    titleInput = element(by.id('field_title'));
    priceInput = element(by.id('field_price'));
    typeInput = element(by.id('field_type'));
    expiryInput = element(by.id('field_expiry'));
    totalChaptersInput = element(by.id('field_totalChapters'));
    discountInput = element(by.id('field_discount'));
    activatedInput = element(by.id('field_activated'));
    createdDateInput = element(by.id('field_createdDate'));
    createdByInput = element(by.id('field_createdBy'));
    lastUpdatedDateInput = element(by.id('field_lastUpdatedDate'));
    lastUpdatedByInput = element(by.id('field_lastUpdatedBy'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setTitleInput(title) {
        await this.titleInput.sendKeys(title);
    }

    async getTitleInput() {
        return this.titleInput.getAttribute('value');
    }

    async setPriceInput(price) {
        await this.priceInput.sendKeys(price);
    }

    async getPriceInput() {
        return this.priceInput.getAttribute('value');
    }

    async setTypeInput(type) {
        await this.typeInput.sendKeys(type);
    }

    async getTypeInput() {
        return this.typeInput.getAttribute('value');
    }

    async setExpiryInput(expiry) {
        await this.expiryInput.sendKeys(expiry);
    }

    async getExpiryInput() {
        return this.expiryInput.getAttribute('value');
    }

    async setTotalChaptersInput(totalChapters) {
        await this.totalChaptersInput.sendKeys(totalChapters);
    }

    async getTotalChaptersInput() {
        return this.totalChaptersInput.getAttribute('value');
    }

    async setDiscountInput(discount) {
        await this.discountInput.sendKeys(discount);
    }

    async getDiscountInput() {
        return this.discountInput.getAttribute('value');
    }

    async setActivatedInput(activated) {
        await this.activatedInput.sendKeys(activated);
    }

    async getActivatedInput() {
        return this.activatedInput.getAttribute('value');
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

export class PackagesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-packages-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-packages'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
