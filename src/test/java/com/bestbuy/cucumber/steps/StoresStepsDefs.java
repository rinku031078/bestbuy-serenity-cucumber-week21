package com.bestbuy.cucumber.steps;

import com.bestbuy.bestbuyinfo.StoresSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class StoresStepsDefs {

    static String name = "Crawley";
    static String type = "BigBox";
    static String address = "100 Downland Drive";
    static String address2 = "London Road";
    static String city = "Crawley";
    static String state = "Sussex";
    static String zip = "55305";
    static double lat = 44.969696;
    static double lng = -93.445679;
    static String hours= "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int storeId;

    static ValidatableResponse response;

    @Steps
    StoresSteps storesSteps;


    @When("^I sends GET request to the Stores Endpoints$")
    public void iSendsGETRequestToTheStoresEndpoints() {
        response = storesSteps.getAllStoresInfo();
    }

    @Then("^I must get valid response with status code (\\d+)$")
    public void iMustGetValidResponseWithStatusCode(int arg0) {
        response.statusCode(200).log().all();
    }


    @When("^I create a new store by providing required informtion$")
    public void iCreateANewStoreByProvidingRequiredInformtion() {
        response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng,hours);
        response.statusCode(201).log().all();
        storeId = response.extract().path("id");
        System.out.println("Id of store is: " + storeId);
    }

    @Then("^I verify that if store is created with name$")
    public void iVerifyThatIfStoreIsCreated() {
        response = storesSteps.getStoreById(storeId);
        response.statusCode(200).log().all();
        assertThat(response.extract().body().jsonPath().get("name"), equalTo(name));
    }

    @When("^I update store information with name and address$")
    public void iUpdateStoreInformationWithNameAndAddress() {
        name = name + " (Updated)";
        address = address + " (Updated)";
        ValidatableResponse response = storesSteps.updateStore(storeId, name, type, address, address2, city, state, zip, lat, lng, hours);
        response.statusCode(200);
    }

    @Then("^I verify if the store information is updated$")
    public void iVerifyIfTheStoreInformationIsUpdated() {
        ValidatableResponse response =  storesSteps.getStoreById(storeId).statusCode(200).log().all();
        assertThat(response.extract().body().jsonPath().get("name"), equalTo(name));
        assertThat(response.extract().body().jsonPath().get("address"), equalTo(address));
    }

    @When("^I delete newly created store with id$")
    public void iDeleteNewlyCreatedStoreWithId() {
        storesSteps.deleteStore(storeId).statusCode(200);
    }

    @Then("^I verify if the store is deleted$")
    public void iVerifyIfTheStoreIsDeleted() {
        storesSteps.getStoreById(storeId).statusCode(404);
    }
}
