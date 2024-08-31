import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import reponseObject.ResponseToken;
import reponseObject.ResponseUser;
import requestObject.RequestUser;

public class CreateUserTest {

    public String baseURI = "https://demoqa.com";
    public RequestUser requestBody;

    @Test
    public void testMethod(){
        System.out.println("====== STEP 1: Create account ======");
        createAccount();

        System.out.println("====== STEP 2: Generate token ======");
        generateToken();

    }

    public void createAccount(){

        //definesc configurarea clientului
        //definim un request
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(baseURI);

        requestBody =  new RequestUser("src/test/resources/createUser.json");

        //adaugam request body
        request.body(requestBody);

        //executam requestul de tip POST la un endpoint specific
        Response response = request.post("/Account/v1/User");

        //validam response status code
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 201);

        Assert.assertTrue(response.getStatusLine().contains("Created"));

        ResponseUser responseBody= response.getBody().as(ResponseUser.class);
        Assert.assertTrue(responseBody.getUsername().equals(requestBody.getUserName()));
        System.out.println(responseBody);
    }

    public void generateToken(){

        //definesc configurarea clientului
        //definim un request
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(baseURI);

        //adaugam request body
        request.body(requestBody);

        //executam requestul de tip POST la un endpoint specific
        Response response = request.post("/Account/v1/GenerateToken");

        //validam response status code
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertTrue(response.getStatusLine().contains("OK"));

        ResponseToken responseBody=response.getBody().as(ResponseToken.class);

        System.out.println(responseBody.getToken());

        System.out.println(responseBody);

        System.out.println(response.getHeaders());

    }
}
