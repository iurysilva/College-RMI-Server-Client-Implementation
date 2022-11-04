import java.rmi.Naming;

public class CompressorServer {
    public CompressorServer() {
        try {
            Compressor obj = new CompressorImp() {
            };
            Naming.rebind("//68.183.143.117:33419/CompressionService", obj);
        }
        catch( Exception e) {
            System.out.println("Erro: " + e);
        }
    }

    public static void main( String[] args ) {
        new CompressorServer();
    }
}
