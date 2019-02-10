import { element, by, ElementFinder } from 'protractor';

export class UserAccountComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-user-account div table .btn-danger'));
    title = element.all(by.css('jhi-user-account div h2#page-heading span')).first();

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

export class UserAccountUpdatePage {
    pageTitle = element(by.id('jhi-user-account-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    remainingChaptersInput = element(by.id('field_remainingChapters'));
    currPackageStartDateInput = element(by.id('field_currPackageStartDate'));
    currPackageEndDateInput = element(by.id('field_currPackageEndDate'));
    userDiscountInput = element(by.id('field_userDiscount'));
    activatedInput = element(by.id('field_activated'));
    accountTypeSelect = element(by.id('field_accountType'));
    perDayChapterLimitInput = element(by.id('field_perDayChapterLimit'));
    createdDateInput = element(by.id('field_createdDate'));
    createdByInput = element(by.id('field_createdBy'));
    lastUpdatedDateInput = element(by.id('field_lastUpdatedDate'));
    lastUpdatedByInput = element(by.id('field_lastUpdatedBy'));
    userSelect = element(by.id('field_user'));
    currentPackageSelect = element(by.id('field_currentPackage'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setRemainingChaptersInput(remainingChapters) {
        await this.remainingChaptersInput.sendKeys(remainingChapters);
    }

    async getRemainingChaptersInput() {
        return this.remainingChaptersInput.getAttribute('value');
    }

    async setCurrPackageStartDateInput(currPackageStartDate) {
        await this.currPackageStartDateInput.sendKeys(currPackageStartDate);
    }

    async getCurrPackageStartDateInput() {
        return this.currPackageStartDateInput.getAttribute('value');
    }

    async setCurrPackageEndDateInput(currPackageEndDate) {
        await this.currPackageEndDateInput.sendKeys(currPackageEndDate);
    }

    async getCurrPackageEndDateInput() {
        return this.currPackageEndDateInput.getAttribute('value');
    }

    async setUserDiscountInput(userDiscount) {
        await this.userDiscountInput.sendKeys(userDiscount);
    }

    async getUserDiscountInput() {
        return this.userDiscountInput.getAttribute('value');
    }

    async setActivatedInput(activated) {
        await this.activatedInput.sendKeys(activated);
    }

    async getActivatedInput() {
        return this.activatedInput.getAttribute('value');
    }

    async setAccountTypeSelect(accountType) {
        await this.accountTypeSelect.sendKeys(accountType);
    }

    async getAccountTypeSelect() {
        return this.accountTypeSelect.element(by.css('option:checked')).getText();
    }

    async accountTypeSelectLastOption() {
        await this.accountTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setPerDayChapterLimitInput(perDayChapterLimit) {
        await this.perDayChapterLimitInput.sendKeys(perDayChapterLimit);
    }

    async getPerDayChapterLimitInput() {
        return this.perDayChapterLimitInput.getAttribute('value');
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

    async userSelectLastOption() {
        await this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectOption(option) {
        await this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    async getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    async currentPackageSelectLastOption() {
        await this.currentPackageSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async currentPackageSelectOption(option) {
        await this.currentPackageSelect.sendKeys(option);
    }

    getCurrentPackageSelect(): ElementFinder {
        return this.currentPackageSelect;
    }

    async getCurrentPackageSelectedOption() {
        return this.currentPackageSelect.element(by.css('option:checked')).getText();
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

export class UserAccountDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-userAccount-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-userAccount'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
