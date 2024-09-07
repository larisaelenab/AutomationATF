package services;

import client.APIClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import responseObject.ResponseGetUser;
import responseObject.ResponseToken;
import responseObject.ResponseUser;
import requestObject.RequestUser;

public class AccountService {

    public ResponseUser createAccount(RequestUser requestBody){

        RequestSpecification request = RestAssured.given();
        request.body(requestBody);

        Response response = performRequest("POST", request, "/Account/v1/User");

        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 201);

        Assert.assertTrue(response.getStatusLine().contains("Created"));

        ResponseUser responseBody= response.getBody().as(ResponseUser.class);
        Assert.assertTrue(responseBody.getUsername().equals(requestBody.getUserName()));
        System.out.println(responseBody);
        return responseBody;
    }

    public ResponseToken generateToken(RequestUser requestBody){
        RequestSpecification request = RestAssured.given();
        request.body(requestBody);
        Response response = performRequest("POST", request,"/Account/v1/GenerateToken");
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertTrue(response.getStatusLine().contains("OK"));

        ResponseToken responseBody = response.getBody().as(ResponseToken.class);
        System.out.println(responseBody.getToken());
        System.out.println(responseBody);
        return responseBody;


    }

    public void validateAccountBE(String token, String userId){
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);

        Response response = performRequest("GET", request, "/Account/v1/User/" + userId);

        ResponseGetUser responseGetUser=response.getBody().as(ResponseGetUser.class);

        System.out.println(responseGetUser);

    }

    public void deleteAccountBE(String token, String userId){
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);

        Response response = performRequest("DELETE", request, "/Account/v1/User/" + userId);

        ResponseGetUser responseGetUser=response.getBody().as(ResponseGetUser.class);

        System.out.println(responseGetUser);
    }

    private Response performRequest(String requestType, RequestSpecification requestSpecification, String endpoint){

        return new APIClient().performRequest(requestType, requestSpecification, endpoint);
    }
}
