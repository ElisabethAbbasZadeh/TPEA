import org.json.JSONException;
import java.io.*;
import java.net.*;
import java.util.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;
import java.security.SignatureException;


public class Client {
    protected Socket socketOfClient;
    protected DataOutputStream os;
    protected DataInputStream is;

    protected int tour;

    private ArrayList<Lettre> lettres;

    protected boolean traitementMessage(String msg) throws JSONException {
        if (Messages.isNextTurn(msg)) {
            tour = Messages.nextTurn(msg);
            return true;
        }
        else if(Messages.isInjectRawOP(msg)){
            System.out.println("Message reçu du serveur : "
                    + Messages.injectRawOP(msg));
            return true;
        }

        return false;
    }

    public void readingInChanel(){
        Thread recevoir = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg;
                try {
                    do {
                        msg = Util.readMsg(is);
                        if(traitementMessage(msg));
                        else{
                            System.out.println("Commande serveur non reconnue.");
                        }
                    }while(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("Serveur déconnecté");
                try {
                    closeConnection();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        recevoir.start();
    }

    public Client (String serverHost, int port) throws JSONException, UnknownHostException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        // Send a request to connect to the server is listening
        // on machine 'localhost' port 9999.
        socketOfClient = new Socket(serverHost, port);

        // Create output stream at the client (to send data to the server)
        os = new DataOutputStream(socketOfClient.getOutputStream());

        // Input stream at Client (Receive data from the server).
        is = new DataInputStream(socketOfClient.getInputStream());

        lettres = new ArrayList<Lettre>();

        readingInChanel();
    }


    public void closeConnection() throws IOException {
    	os.close();
    	is.close();
    	socketOfClient.close();
    }

    public static void main(String[] args) throws UnknownHostException, JSONException, IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        if(args.length!=2) {
            System.out.println("usage : command serveur port");
            System.exit(-1);
        }
        // Server Host
        final String serverHost = args[0];
        final int port = Integer.valueOf(args[1]);
        Client c = new Client(serverHost, port);
    }
}