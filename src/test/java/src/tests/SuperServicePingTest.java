package src.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit.Response;
import src.core.Environment;
import src.core.SuperService;

import static org.assertj.core.api.Assertions.assertThat;

public class SuperServicePingTest {

    private SuperService service = Environment.getService();

    @Test
    @DisplayName("Пинг")
    public void ping() throws Exception {
        Response<Object> response = service.ping().execute();
        assertThat(response.isSuccess()).isTrue();
    }
}
