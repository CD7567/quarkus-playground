package com.cd7567.quarkus_playground;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import com.cd7567.quarkus_playground.conf.HelloConfPojo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class HelloResourceTest {
    @Inject
    HelloConfPojo conf;

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    public void testHelloPoliteEndpoint() {
        given()
                .when().get("/hello/polite/tester")
                .then()
                .statusCode(200)
                .body(is(conf.message().polite() + "tester"));
    }

    @Test
    public void testHelloRegularEndpoint() {
        given()
                .when().get("/hello/regular/tester")
                .then()
                .statusCode(200)
                .body(is(conf.message().regular() + "tester"));
    }
}