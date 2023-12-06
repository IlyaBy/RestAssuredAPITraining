
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String url = "https://reqres.in/";

    @Test
    @DisplayName("checkAvatarContainsIdTest")
    public void checkAvatarContainsIdTest(){

        Specifications.installSpecification(Specifications.requestSpec(url), Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(x-> Assertions.assertTrue(x.getAvatarFileName().contains(x.getId().toString())));
        Assertions.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));

    }
    @Test
    @DisplayName("successUserRegistrationTest")
    public void successUserRegistrationTest(){
        Integer UserId = 4;
        String UserPassword = "QpwL5tke4Pnpja7X4";
        Specifications.installSpecification(Specifications.requestSpec(url), Specifications.responseSpecOK200());
        UserRegistrationData user = new UserRegistrationData("eve.holt@reqres.in","pistol");
        SuccessUserRegistration successUserReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then()
                .log().all()
                .extract().as(SuccessUserRegistration.class);
        Assertions.assertNotNull(successUserReg.getId());
        Assertions.assertNotNull(successUserReg.getToken());
        Assertions.assertEquals(UserId, successUserReg.getId());
        Assertions.assertEquals(UserPassword, successUserReg.getToken());
    }
    @Test
    @DisplayName("unSuccessUserRegistrationTest")
    public void unSuccessUserRegistrationTest(){
        Specifications.installSpecification(Specifications.requestSpec(url),Specifications.responseSpecError400());
        UserRegistrationData failedUser = new UserRegistrationData("sydney@fife");
        FailedUserRegistration failedUserReg = given()
                .body(failedUser)
                .when()
                .post("api/register")
                .then()  //.assertThat().statusCode(400) проверить статус ошибки, если не указана спецификация
                .log().body()
                .extract().as(FailedUserRegistration.class);
        Assertions.assertNotNull(failedUserReg.getError());
        Assertions.assertEquals("Missing password", failedUserReg.getError());
    }
    @Test
    @DisplayName("checkSortedYearsTest")
    public void checkSortedYearsTest(){
        Specifications.installSpecification(Specifications.requestSpec(url), Specifications.responseSpecOK200());
        List<ListOfColors> listOfColors = given()
                .when()
                .get("api/unknown")
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", ListOfColors.class);

        List<Integer> years = listOfColors.stream().map(ListOfColors::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
        Assertions.assertEquals(years, sortedYears);
    }
    @Test
    @DisplayName("deleteUserTest")
    public void deleteUserTest(){
        Specifications.installSpecification(Specifications.requestSpec(url), Specifications.responseSpecManual(204));
        given().when().delete("api/users/2")
                .then()
                .assertThat().statusCode(204)
                .log().all();
    }

}
