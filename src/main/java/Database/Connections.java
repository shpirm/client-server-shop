package Database;

public class Connections {
    String login;
    String host;
    int port;

    public Connections(String login, String host, int port) {
        this.login = login;
        this.host = host;
        this.port = port;
    }

    public String getLogin() {
        return login;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
