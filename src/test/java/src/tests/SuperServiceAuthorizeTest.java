package src.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import retrofit.Response;
import src.core.Environment;
import src.core.SuperService;

import static org.assertj.core.api.Assertions.assertThat;
import static src.core.ConfigParams.username;
import static src.core.ConfigParams.username_password;


public class SuperServiceAuthorizeTest {

    SuperService service = Environment.getService();

    @ParameterizedTest
    @CsvSource({
            username + ", sdfg&sd",
            "sdfg&sd, " + username_password,
            ",''",
            "'',"
    }
    )
    @DisplayName("Попытки сохранения данных с ошибочными параметрами login и password")
    void negativeAuthorize(String login, String password) throws Exception {
        Response<Object> response = service.authorize(login, password).execute();
        assertThat(response.isSuccess()).isFalse();
    }

    @Test
    @DisplayName("Авторизация")
    public void authorize() throws Exception {
        Response<Object> response = service.authorize(username, username_password).execute();
        assertThat(response.isSuccess()).isTrue();
    }
}
