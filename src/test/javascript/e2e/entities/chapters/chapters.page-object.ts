import { element, by, ElementFinder } from 'protractor';

export class ChaptersComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-chapters div table .btn-danger'));
    title = element.all(by.css('jhi-chapters div h2#page-heading span')).first();

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

export class ChaptersUpdatePage {
    pageTitle = element(by.id('jhi-chapters-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    statusSelect = element(by.id('field_status'));
    activatedInput = element(by.id('field_activated'));
    createdDateInput = element(by.id('field_createdDate'));
    createdByInput = element(by.id('field_createdBy'));
    lastUpdatedDateInput = element(by.id('field_lastUpdatedDate'));
    lastUpdatedByInput = element(by.id('field_lastUpdatedBy'));
    initiatorSelect = element(by.id('field_initiator'));
    partnerSelect = element(by.id('field_partner'));

    async getPageTitle() {
        return this.pageTitle.getText();
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

    async initiatorSelectLastOption() {
        await this.initiatorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async initiatorSelectOption(option) {
        await this.initiatorSelect.sendKeys(option);
    }

    getInitiatorSelect(): ElementFinder {
        return this.initiatorSelect;
    }

    async getInitiatorSelectedOption() {
        return this.initiatorSelect.element(by.css('option:checked')).getText();
    }

    async partnerSelectLastOption() {
        await this.partnerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async partnerSelectOption(option) {
        await this.partnerSelect.sendKeys(option);
    }

    getPartnerSelect(): ElementFinder {
        return this.partnerSelect;
    }

    async getPartnerSelectedOption() {
        return this.partnerSelect.element(by.css('option:checked')).getText();
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

export class ChaptersDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-chapters-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-chapters'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
