import { element, by, ElementFinder } from 'protractor';

export class IDBUserComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-idb-user div table .btn-danger'));
    title = element.all(by.css('jhi-idb-user div h2#page-heading span')).first();

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

export class IDBUserUpdatePage {
    pageTitle = element(by.id('jhi-idb-user-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    emailInput = element(by.id('field_email'));
    passwordInput = element(by.id('field_password'));
    activatedInput = element(by.id('field_activated'));
    accessTokenInput = element(by.id('field_accessToken'));
    sessionTokenInput = element(by.id('field_sessionToken'));
    lastLoginDateInput = element(by.id('field_lastLoginDate'));
    lastDeactivatedDateInput = element(by.id('field_lastDeactivatedDate'));
    userRolesSelect = element(by.id('field_userRoles'));
    verifiedInput = element(by.id('field_verified'));
    verificationMethodInput = element(by.id('field_verificationMethod'));
    isReportedScamInput = element(by.id('field_isReportedScam'));
    lastLogoutInput = element(by.id('field_lastLogout'));
    lastActivatedDateInput = element(by.id('field_lastActivatedDate'));
    createdDateInput = element(by.id('field_createdDate'));
    createdByInput = element(by.id('field_createdBy'));
    lastUpdatedDateInput = element(by.id('field_lastUpdatedDate'));
    lastUpdatedByInput = element(by.id('field_lastUpdatedBy'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setPasswordInput(password) {
        await this.passwordInput.sendKeys(password);
    }

    async getPasswordInput() {
        return this.passwordInput.getAttribute('value');
    }

    getActivatedInput() {
        return this.activatedInput;
    }
    async setAccessTokenInput(accessToken) {
        await this.accessTokenInput.sendKeys(accessToken);
    }

    async getAccessTokenInput() {
        return this.accessTokenInput.getAttribute('value');
    }

    async setSessionTokenInput(sessionToken) {
        await this.sessionTokenInput.sendKeys(sessionToken);
    }

    async getSessionTokenInput() {
        return this.sessionTokenInput.getAttribute('value');
    }

    async setLastLoginDateInput(lastLoginDate) {
        await this.lastLoginDateInput.sendKeys(lastLoginDate);
    }

    async getLastLoginDateInput() {
        return this.lastLoginDateInput.getAttribute('value');
    }

    async setLastDeactivatedDateInput(lastDeactivatedDate) {
        await this.lastDeactivatedDateInput.sendKeys(lastDeactivatedDate);
    }

    async getLastDeactivatedDateInput() {
        return this.lastDeactivatedDateInput.getAttribute('value');
    }

    async setUserRolesSelect(userRoles) {
        await this.userRolesSelect.sendKeys(userRoles);
    }

    async getUserRolesSelect() {
        return this.userRolesSelect.element(by.css('option:checked')).getText();
    }

    async userRolesSelectLastOption() {
        await this.userRolesSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    getVerifiedInput() {
        return this.verifiedInput;
    }
    async setVerificationMethodInput(verificationMethod) {
        await this.verificationMethodInput.sendKeys(verificationMethod);
    }

    async getVerificationMethodInput() {
        return this.verificationMethodInput.getAttribute('value');
    }

    getIsReportedScamInput() {
        return this.isReportedScamInput;
    }
    async setLastLogoutInput(lastLogout) {
        await this.lastLogoutInput.sendKeys(lastLogout);
    }

    async getLastLogoutInput() {
        return this.lastLogoutInput.getAttribute('value');
    }

    async setLastActivatedDateInput(lastActivatedDate) {
        await this.lastActivatedDateInput.sendKeys(lastActivatedDate);
    }

    async getLastActivatedDateInput() {
        return this.lastActivatedDateInput.getAttribute('value');
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

export class IDBUserDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-iDBUser-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-iDBUser'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
