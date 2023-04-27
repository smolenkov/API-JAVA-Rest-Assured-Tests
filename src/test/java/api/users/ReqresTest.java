package api.users;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private static final String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIDTests(){
        List<UserData> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL+"api/users?page=2")
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        users.forEach(x-> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assertions.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("reqres.in")));

        List<String> avatar = users.stream().map(UserData::getAvatar).toList();
        List<String> ids = users.stream().map(x->x.getId().toString()).toList();
        for(int i = 0; i<avatar.size(); i++){
            Assertions.assertTrue(avatar.get(i).contains(ids.get(i)));
        }
    }

}
