package com.reifywise.restassured;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.concurrent.TimeUnit;

public class DemoTest {

    @Before
    public void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void whenMeasureResponseTime_thenOK(){
        Response response = RestAssured.get("/list");
        long timeInMS = response.time();
        long timeInS = response.timeIn(TimeUnit.SECONDS);
        assertEquals(timeInS, timeInMS/1000);
    }

    @Test
    public void testCustomerList(){
        get("http://localhost:8080/list")
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    public void testCustomerOne(){

        get("http://localhost:8080/one/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", Matchers.equalTo("john"));
    }

    @Test
    public void testCustomerOne_AssertFail(){
        get("http://localhost:8080/one/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", Matchers.equalTo("frank"));
    }

}
