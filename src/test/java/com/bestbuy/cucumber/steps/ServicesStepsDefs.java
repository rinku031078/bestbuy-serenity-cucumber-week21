package com.bestbuy.cucumber.steps;

import com.bestbuy.bestbuyinfo.ServicesSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.hamcrest.CoreMatchers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class ServicesStepsDefs {

    static String name = "Apple Shop";
    static Integer serviceId;

    ValidatableResponse response;

    @Steps
    ServicesSteps servicesSteps;

    @When("^I sends GET request to the Services Endpoints$")
    public void iSendsGETRequestToTheServicesEndpoints() {
        response = servicesSteps.getAllService();
    }

    @Then("^I verify that valid response with status code (\\d+)$")
    public void iVerifyThatValidResponseWithStatusCode(int arg0) {
        response.statusCode(200);
    }

    @When("^I create a new Service by providing required informtion$")
    public void iCreateANewServiceByProvidingRequiredInformtion() {
        response = servicesSteps.createService(name);
        response.log().all().statusCode(201);
        serviceId = response.extract().path("id");
        System.out.println("service Id is: " + serviceId);
    }

    @Then("^I verify that if Service is created with name$")
    public void iVerifyThatIfServiceIsCreatedWithName() {
        response = servicesSteps.getServiceById(serviceId);
        response.statusCode(200);
        assertThat(response.extract().body().jsonPath().get("name"), equalTo(name));
        assertThat(response.extract().body().jsonPath().getInt("id"), equalTo(serviceId));
    }

    @When("^I update Service information with name$")
    public void iUpdateServiceInformationWithName() {
        name = name + " _UPDATED";
        response = servicesSteps.updateService(name,serviceId);
        response.statusCode(200);
    }

    @Then("^I verify if the Service information is updated$")
    public void iVerifyIfTheServiceInformationIsUpdated() {
        response = servicesSteps.getServiceById(serviceId);
        assertThat(response.extract().body().jsonPath().get("name"), CoreMatchers.equalTo(name));
    }

    @When("^I delete newly created Service with id$")
    public void iDeleteNewlyCreatedServiceWithId() {
        servicesSteps.deleteService(serviceId).statusCode(200);
    }

    @Then("^I verify if the Service is deleted$")
    public void iVerifyIfTheServiceIsDeleted() {
        servicesSteps.getServiceById(serviceId).statusCode(404);
    }
}
