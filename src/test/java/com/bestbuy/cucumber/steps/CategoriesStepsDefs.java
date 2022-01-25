package com.bestbuy.cucumber.steps;

import com.bestbuy.bestbuyinfo.CategoriesSteps;
import com.bestbuy.utils.TestUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class CategoriesStepsDefs {

    static String name = "Electronics & Computers" + TestUtils.getRandomValue();
    static String id = TestUtils.getRandomName();
    static String categoryId;

    static ValidatableResponse response;

    @Steps
    CategoriesSteps categoriesSteps;

    @When("^I send GET request to the Categories Endpoint$")
    public void iSendGETRequestToTheCategoriesEndpoint() {
        response = categoriesSteps.getAllCategories();
    }

    @Then("^I verify response status code is (\\d+)$")
    public void iVerifyResponseStatusCodeIs(int arg0) {
        response.statusCode(200).log().all();
    }

    @When("^I create a new category by providing information$")
    public void iCreateANewCategoryByProvidingInformation() {
        response = categoriesSteps.createCategory(name,id);
        response.log().all().statusCode(201);
        categoryId = response.extract().path("id");
        System.out.println("category Id is: " + categoryId);
    }

    @Then("^I verify that the category with name is created$")
    public void iVerifyThatTheCategoryWithNameIsCreated() {
        response = categoriesSteps.getCategoryById(categoryId);
        response.statusCode(200).log().all();
        categoryId = response.extract().body().path("id");
        assertThat(response.extract().body().path("id"), equalTo(categoryId));
        assertThat(response.extract().body().jsonPath().get("name"), equalTo(name));
    }

    @When("^I update category information with name$")
    public void iUpdateCategoryInformationWithName() {
        name = name + " _UPDATED";
        categoriesSteps.updateCategory(name, categoryId).statusCode(200).log().all();
    }

    @Then("^I verify if the category information is updated$")
    public void iVerifyIfTheCategoryInformationIsUpdated() {
        response = categoriesSteps.getCategoryById(categoryId);
        response.statusCode(200).log().all();
        assertThat(response.extract().body().jsonPath().get("name"), equalTo(name));
    }

    @When("^I delete newly created category with id$")
    public void iDeleteNewlyCreatedCategoryWithId() {
        categoriesSteps.deleteCategory(categoryId).statusCode(200).log().all();
    }

    @Then("^I verify if the category is deleted$")
    public void iVerifyIfTheCategoryIsDeleted() {
        categoriesSteps.getCategoryById(categoryId).statusCode(404).log().all();
    }
}
