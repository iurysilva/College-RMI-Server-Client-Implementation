import java.rmi.Naming;

public class CompressorServer {
    public CompressorServer() {
        try {
            Compressor obj = new CompressorImp() {
            };
            Naming.rebind("//192.168.100.35/CompressionService", obj);
        }
        catch( Exception e) {
            System.out.println("Erro: " + e);
        }
    }

    public static void main( String[] args ) {
        new CompressorServer();
    }
}
