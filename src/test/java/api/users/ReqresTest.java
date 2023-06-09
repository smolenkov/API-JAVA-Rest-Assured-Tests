package api.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.Clock;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private static final String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIDTests() {
        Specifications.InstallSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOk200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        users.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assertions.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("reqres.in")));

        List<String> avatar = users.stream().map(UserData::getAvatar).toList();
        List<String> ids = users.stream().map(x -> x.getId().toString()).toList();
        for (int i = 0; i < avatar.size(); i++) {
            Assertions.assertTrue(avatar.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void successUserRegTest() {
        Integer UserId = 4;
        String UserPassword = "QpwL5tke4Pnpja7X4";
        Specifications.InstallSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOk200());
        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessReg successUserReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then()
                .log().all()
                .extract().as(SuccessReg.class);
        Assertions.assertNotNull(successUserReg.getId());
        Assertions.assertNotNull(successUserReg.getToken());

        Assertions.assertEquals(UserId, successUserReg.getId());
        Assertions.assertEquals(UserPassword, successUserReg.getToken());
    }

    @Test
    public void unSuccessUserRegTest() {
        Specifications.InstallSpecification(Specifications.requestSpec(URL), Specifications.responseSpecError400());
        Register user = new Register("sydney@fife", "");
        UnSuccessUserReg unSuccessUserReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then()
                .log().all()
                .extract().as(UnSuccessUserReg.class);
        Assertions.assertEquals("Missing password", unSuccessUserReg.getError());
    }

    @Test
    public void sortedYearsTest(){
        Specifications.InstallSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOk200());
        List<ColorsData> colors = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);
        List<Integer> years = colors.stream().map(ColorsData::getYear).toList();
        List<Integer> sortedYears = years.stream().sorted().toList();
        Assertions.assertEquals(years, sortedYears);
    }

    @Test
    public void deleteUserTest(){
        Specifications.InstallSpecification(Specifications.requestSpec(URL), Specifications.responseSpecCustom(204));
        given()
            .when()
            .delete("api/users?page=2")
            .then()
            .log().all();
    }

    @Test
    public void checkServerAndPcDateTest(){
        Specifications.InstallSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOk200());
        UserTime user = new UserTime("morpheus","zion resident");
        UserTimeResponse response = given()
                .body(user)
                .when()
                .put("/api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);

        String regex = ":.*$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex,"");
        System.out.println(currentTime);

        Assertions.assertEquals(response.getUpdatedAt().replaceAll(regex,""),currentTime);
    }

}
