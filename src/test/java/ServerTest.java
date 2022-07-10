import Structure.Commands.UserCommand;
import Structure.ServerClient.ClientTCP;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ServerTest {

    @Test
    void clientTest() throws IOException, InterruptedException {
        ClientTCP clientTCP = new ClientTCP();
        clientTCP.startConnection();

        clientTCP.sendMessage(UserCommand.GROUP_INSERT, new JSONObject());
        clientTCP.sendMessage(UserCommand.GROUP_UPDATE, new JSONObject());
    }
}
