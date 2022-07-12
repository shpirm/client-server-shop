package Structure.Client;

public class User {

    private static User instance;
    private User() {}

    private ClientTCP connection;
    public static User getInstance(){
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void setConnection(ClientTCP connection) {
        this.connection = connection;
    }

    public ClientTCP getConnection() {
        return connection;
    }
}
