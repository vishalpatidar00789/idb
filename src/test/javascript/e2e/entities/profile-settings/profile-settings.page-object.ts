import { element, by, ElementFinder } from 'protractor';

export class ProfileSettingsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-profile-settings div table .btn-danger'));
    title = element.all(by.css('jhi-profile-settings div h2#page-heading span')).first();

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

export class ProfileSettingsUpdatePage {
    pageTitle = element(by.id('jhi-profile-settings-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    keyInput = element(by.id('field_key'));
    isPublicInput = element(by.id('field_isPublic'));
    createdDateInput = element(by.id('field_createdDate'));
    createdByInput = element(by.id('field_createdBy'));
    lastUpdatedDateInput = element(by.id('field_lastUpdatedDate'));
    lastUpdatedByInput = element(by.id('field_lastUpdatedBy'));
    userProfileSelect = element(by.id('field_userProfile'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setKeyInput(key) {
        await this.keyInput.sendKeys(key);
    }

    async getKeyInput() {
        return this.keyInput.getAttribute('value');
    }

    getIsPublicInput() {
        return this.isPublicInput;
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

    async userProfileSelectLastOption() {
        await this.userProfileSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userProfileSelectOption(option) {
        await this.userProfileSelect.sendKeys(option);
    }

    getUserProfileSelect(): ElementFinder {
        return this.userProfileSelect;
    }

    async getUserProfileSelectedOption() {
        return this.userProfileSelect.element(by.css('option:checked')).getText();
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

export class ProfileSettingsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-profileSettings-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-profileSettings'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
