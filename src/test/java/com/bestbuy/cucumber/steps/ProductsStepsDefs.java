package com.bestbuy.cucumber.steps;

import com.bestbuy.bestbuyinfo.ProductsSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class ProductsStepsDefs {


    static Integer shipping = 20;
    static String manufacturer = "Apple";
    static String url = "This is URL";
    static String image = "This is Image";
    static int productId;

    static ValidatableResponse response;

    @Steps
    ProductsSteps productsSteps;

    @When("^User sends GET request to the Products Endpoints$")
    public void userSendsGETRequestToTheProductsEndpoints() {
        response = productsSteps.getAllProducts();
    }

    @Then("^User must get back a valid status code 200$")
    public void iUserMustGetBackAValidStatusCode() {
        response.statusCode(200).log().all();
    }

    @When("^I create a new product by providing the information name \"([^\"]*)\" type \"([^\"]*)\" upc \"([^\"]*)\" price \"([^\"]*)\"description \"([^\"]*)\" model \"([^\"]*)\"$")
    public void iCreateANewProductByProvidingTheInformationNameTypeUpcPriceDescriptionModel(String name, String type, String upc, Double price, String description, String model) {
        response = productsSteps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        response.statusCode(201).log().all();
        productId = response.extract().path("id");
        System.out.println("product id is: " + productId);
    }

    @Then("^I verify that product is created with name \"([^\"]*)\"$")
    public void iVerifyThatProductIsCreatedWithName(String name) {
        response = productsSteps.getProductById(productId);
        response.statusCode(200).log().all();
        assertThat(response.extract().body().jsonPath().get("name"), equalTo(name));
        assertThat(response.extract().body().jsonPath().getInt("id"), equalTo(productId));
    }

    @When("^I update the product with name \"([^\"]*)\" type \"([^\"]*)\" upc \"([^\"]*)\" price \"([^\"]*)\"description \"([^\"]*)\" model \"([^\"]*)\"$")
    public void iUpdateTheProductWithNameTypeUpcPriceDescriptionModel(String name, String type, String upc, Double price, String description, String model)  {
        productsSteps.updateProduct(productId, name, type, price, shipping, upc, description, manufacturer, model, url, image);
    }

    @Then("^I verify that the information with name \"([^\"]*)\" is updated in the product$")
    public void iVerifyThatTheInformationWithNameIsUpdatedInTheProduct(String name) {
        response = productsSteps.getProductById(productId).statusCode(200).log().all();
        assertThat(response.extract().body().jsonPath().get("name"), equalTo(name));
    }

    @When("^I delete the product by product id$")
    public void iDeleteTheProductByProductId() {
        productsSteps.deleteProduct(productId).statusCode(200);
    }

    @Then("^I verify the product is deleted successfully$")
    public void iVerifyTheProductIsDeletedSuccessfully() {
        productsSteps.getProductById(productId).statusCode(404);
    }

}
