package client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIClient {

    //clasa in care configuram clientul
    //facem o metoda in care setam configurarile de client
    //facem o metoda in care setam actiunea pe care o poate face clientul - get, post, put, delete


    public String baseURI = "https://demoqa.com";

    public Response performRequest(String requestType, RequestSpecification requestSpecification, String endpoint){
        switch (requestType){
            case "post":
                return prepareClient(requestSpecification).post(endpoint);
            case "get":
                return prepareClient(requestSpecification).get(endpoint);
            case "delete":
                return prepareClient(requestSpecification).delete(endpoint);
        }
        return null;
    }

    public RequestSpecification prepareClient(RequestSpecification requestSpecification){

        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.baseUri(baseURI);
        return requestSpecification;
    }
}
