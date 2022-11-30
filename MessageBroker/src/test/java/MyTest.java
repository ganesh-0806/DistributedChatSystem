import org.distributed.connector.ClientManager;
import org.distributed.connector.ServerManager;

public class MyTest {
    public static void main(String[] args) {
        // Start Client Manager
        ClientManager clientManager = new ClientManager(5678);
        clientManager.start();
        // Start Server Manager
        ServerManager serverManager = new ServerManager(8888);
        new Thread(serverManager).start();

        System.out.println("Threads started");

        while(true){

        }
    }
}