import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ClientCompressor {
    public static void main( String[] args ) {
        final File[] fileToSend = new File[1];

        JFrame jFrame = new JFrame("Client");
        jFrame.setSize(450, 450);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jlTitle = new JLabel("File Sender");
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jlFileName = new JLabel("choose a file to send");
        jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
        jlFileName.setBorder(new EmptyBorder(50, 0, 0, 0));
        jlFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButton = new JPanel();
        jpButton.setBorder(new EmptyBorder(75, 0, 10, 0));

        JButton jbSendFile = new JButton("Send File");
        jbSendFile.setPreferredSize(new Dimension(150, 75));
        jbSendFile.setFont(new Font("Arial", Font.BOLD, 20));

        JButton jbChooseFile = new JButton("Choose File");
        jbChooseFile.setPreferredSize(new Dimension(150, 75));
        jbChooseFile.setFont(new Font("Arial", Font.BOLD, 20));

        jpButton.add(jbSendFile);
        jpButton.add(jbChooseFile);

        jbChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Choose a file to send");

                if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    fileToSend[0] = jFileChooser.getSelectedFile();
                    jlFileName.setText("The file you want to send is: " + fileToSend[0].getName());
                }
            }
        });

        jbSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileToSend[0] == null){
                    jlFileName.setText("Please choose a file first.");
                } else{
                    try{
                        FileInputStream fileInputStream = new FileInputStream(fileToSend[0].getAbsolutePath());
                        String fileName = fileToSend[0].getName();
                        byte[] fileContentBytes = new byte[(int)fileToSend[0].length()];
                        fileInputStream.read(fileContentBytes);

                        //System.out.println("Image sent to: " + args[0]);
                        Compressor c = (Compressor) Naming.lookup("//192.168.100.35/CompressionService");

                        byte[] compressedFileContentBytes = c.compress(fileContentBytes);
                        try {
                            FileOutputStream stream = new FileOutputStream("files/compressed_image.jpg");
                            stream.write(compressedFileContentBytes);
                            stream.close();
                            System.out.println("Compressed image was received");
                        }catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }catch (IOException error){
                        error.printStackTrace();
                    }
                    catch ( NotBoundException nbe ) {
                        System.out.println( );
                        System.out.println( "NotBoundException" );
                    }
                    catch ( java.lang.ArithmeticException ae ) {
                        System.out.println( );
                        System.out.println( "java.lang.ArithmeticException" );
                    }
                }
            }
        });

        jFrame.add(jlTitle);
        jFrame.add(jlFileName);
        jFrame.add(jpButton);
        jFrame.setVisible(true);
    }
}