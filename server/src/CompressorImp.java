import java.net.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.util.Iterator;



public class CompressorImp extends java.rmi.server.UnicastRemoteObject implements Compressor {

    public CompressorImp() throws java.rmi.RemoteException{
        super();
    }

    public byte[] compress(byte[] fileContentBytes) throws java.rmi.RemoteException {
        String imageName = "files/image.jpg";
        try{
            FileOutputStream stream = new FileOutputStream(imageName);
            stream.write(fileContentBytes);
            stream.close();

            File input = new File(imageName);
            BufferedImage image = ImageIO.read(input);
            File compressedImageFile = new File("files/compressed_image.jpg");
            OutputStream compressionStream = new FileOutputStream(compressedImageFile);
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = (ImageWriter) writers.next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(compressionStream);
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.15f);  // Change the quality value you prefer
            writer.write(null, new IIOImage(image, null, null), param);
            compressionStream.close();
            ios.close();
            writer.dispose();
            System.out.println("The image was compressed, sending result to client...");

            File input2 = new File("files/compressed_image.jpg");
            FileInputStream fileInputStream = new FileInputStream(input2.getAbsolutePath());
            byte[] compressedFileContentBytes = new byte[(int)input2.length()];
            fileInputStream.read(compressedFileContentBytes);
            return(compressedFileContentBytes);
            //out.writeInt(compressedFileContentBytes.length);

        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return(null);
    }
}
