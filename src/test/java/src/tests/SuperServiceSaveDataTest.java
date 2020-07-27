package src.tests;

import com.jayway.jsonpath.JsonPath;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit.Response;
import src.core.Environment;
import src.core.SuperService;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static src.core.ConfigParams.*;

public class SuperServiceSaveDataTest {

    private static String token;
    private static SuperService service = Environment.getService();

    @BeforeAll
    public static void authorize() throws Exception {
        Response<Object> response = service.authorize(username, username_password).execute();
        assertThat(response.isSuccess()).isTrue();
        token = "Bearer " + JsonPath.read(response.body(), "$.token");
    }

    @Test
    @DisplayName("Сохранение данных")
    public void saveData() throws Exception {
        Response<Object> response = service.saveData(token, payload).execute();
        assertThat(response.isSuccess()).isTrue();
        assertStatusMessage(response);
    }

    @Test
    @DisplayName("Сохранение данных без токена авторизации")
    public void saveDataWithoutToken() throws Exception {
        Response<Object> response = service.saveDataWithoutAuthHeader(payload).execute();
        assertThat(response.isSuccess()).isFalse();
    }

    @Test
    @DisplayName("Сохранение данных без payload")
    public void saveDataWithoutPayload() throws Exception {
        Response<Object> response = service.saveData(token).execute();
        assertThat(response.isSuccess()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "alskjfs", "bearer 32422523dgdf"})
    @DisplayName("Попытки сохранения данных с ошибочным значением токена авторизации")
    public void saveDataWithErrorToken(String token) throws Exception {
        Response<Object> response = service.saveData(token, payload).execute();
        assertThat(response.isSuccess()).isFalse();
    }

    @Test
    @DisplayName("Попытка сохранения данных с просроченным токеном")
    public void saveDataWithOldToken() {
        Awaitility
                .await()
                .atLeast(55, TimeUnit.SECONDS)
                .atMost(75, TimeUnit.SECONDS)
                .pollInterval(10, TimeUnit.SECONDS)
                .until(() -> {
                    Response<Object> response = service.saveData(token, payload).execute();
                    return !response.isSuccess();
                });
    }

    private void assertStatusMessage(Response<Object> response) {
        String status = JsonPath.read(response.body(), "$.status");
        assertThat(status).isEqualTo("OK");
    }
}
