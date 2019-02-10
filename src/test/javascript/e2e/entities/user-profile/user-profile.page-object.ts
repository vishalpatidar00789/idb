import { element, by, ElementFinder } from 'protractor';

export class UserProfileComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-user-profile div table .btn-danger'));
    title = element.all(by.css('jhi-user-profile div h2#page-heading span')).first();

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

export class UserProfileUpdatePage {
    pageTitle = element(by.id('jhi-user-profile-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    screenNameInput = element(by.id('field_screenName'));
    profilePicInput = element(by.id('file_profilePic'));
    genderInput = element(by.id('field_gender'));
    dobInput = element(by.id('field_dob'));
    ageInput = element(by.id('field_age'));
    countryInput = element(by.id('field_country'));
    stateInput = element(by.id('field_state'));
    cityInput = element(by.id('field_city'));
    pincodeInput = element(by.id('field_pincode'));
    personalitySelect = element(by.id('field_personality'));
    interestsSelect = element(by.id('field_interests'));
    offersingsSelect = element(by.id('field_offersings'));
    statusSelect = element(by.id('field_status'));
    createdDateInput = element(by.id('field_createdDate'));
    createdByInput = element(by.id('field_createdBy'));
    lastUpdatedDateInput = element(by.id('field_lastUpdatedDate'));
    lastUpdatedByInput = element(by.id('field_lastUpdatedBy'));
    userSelect = element(by.id('field_user'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setScreenNameInput(screenName) {
        await this.screenNameInput.sendKeys(screenName);
    }

    async getScreenNameInput() {
        return this.screenNameInput.getAttribute('value');
    }

    async setProfilePicInput(profilePic) {
        await this.profilePicInput.sendKeys(profilePic);
    }

    async getProfilePicInput() {
        return this.profilePicInput.getAttribute('value');
    }

    async setGenderInput(gender) {
        await this.genderInput.sendKeys(gender);
    }

    async getGenderInput() {
        return this.genderInput.getAttribute('value');
    }

    async setDobInput(dob) {
        await this.dobInput.sendKeys(dob);
    }

    async getDobInput() {
        return this.dobInput.getAttribute('value');
    }

    async setAgeInput(age) {
        await this.ageInput.sendKeys(age);
    }

    async getAgeInput() {
        return this.ageInput.getAttribute('value');
    }

    async setCountryInput(country) {
        await this.countryInput.sendKeys(country);
    }

    async getCountryInput() {
        return this.countryInput.getAttribute('value');
    }

    async setStateInput(state) {
        await this.stateInput.sendKeys(state);
    }

    async getStateInput() {
        return this.stateInput.getAttribute('value');
    }

    async setCityInput(city) {
        await this.cityInput.sendKeys(city);
    }

    async getCityInput() {
        return this.cityInput.getAttribute('value');
    }

    async setPincodeInput(pincode) {
        await this.pincodeInput.sendKeys(pincode);
    }

    async getPincodeInput() {
        return this.pincodeInput.getAttribute('value');
    }

    async setPersonalitySelect(personality) {
        await this.personalitySelect.sendKeys(personality);
    }

    async getPersonalitySelect() {
        return this.personalitySelect.element(by.css('option:checked')).getText();
    }

    async personalitySelectLastOption() {
        await this.personalitySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setInterestsSelect(interests) {
        await this.interestsSelect.sendKeys(interests);
    }

    async getInterestsSelect() {
        return this.interestsSelect.element(by.css('option:checked')).getText();
    }

    async interestsSelectLastOption() {
        await this.interestsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setOffersingsSelect(offersings) {
        await this.offersingsSelect.sendKeys(offersings);
    }

    async getOffersingsSelect() {
        return this.offersingsSelect.element(by.css('option:checked')).getText();
    }

    async offersingsSelectLastOption() {
        await this.offersingsSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

export class UserProfileDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-userProfile-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-userProfile'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
