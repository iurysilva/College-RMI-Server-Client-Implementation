public interface Compressor extends java.rmi.Remote {
    public byte[] compress( byte[] fileContentBytes ) throws java.rmi.RemoteException;
}
