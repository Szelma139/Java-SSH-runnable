package sample;

import com.jcraft.jsch.*;
import javafx.scene.control.ProgressBar;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class JSshConnection extends Thread {

    String host = "";
    String user = "";
    String password = "";
    String command1 = "ls -l";
    String port = "";
    ProgressBar bar = null;
    progressMonitor monitor = null;
    String filepath= "";
    String remotepath= "";
    String filename = "";

    private JSch jsChannel = null;
    private Session session = null;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;
    private ArrayList<String> pliki = null;

    public ArrayList<String> getPliki() {
        return pliki;
    }

    @Override
    public void run() {
        for (int i = 0; i < pliki.size(); i ++)
        {
            filename = pliki.get(i);
            boolean success = false;
            try {
                System.out.println("Pobieram plik " + filename + " z lokalizacji " + remotepath);
                channel = session.openChannel("sftp");
                channel.connect();
                channelSftp = (ChannelSftp) channel;
                channelSftp.cd(remotepath);
              //  monitor = new progressMonitor(bar);

               // BufferedInputStream bis = new BufferedInputStream(//
                 //  BufferedInputStream bis = new BufferedInputStream(channelSftp.get(filename,monitor));
                BufferedInputStream bis = new BufferedInputStream(channelSftp.get(filename, new progressMonitor(bar)));
                // channelSftp.get(filename, new progressMonitor());
                File newFile = new File(filepath + filename);
                System.out.println(filepath + filename);
                OutputStream os = new FileOutputStream(newFile);
                BufferedOutputStream bos = new BufferedOutputStream(os);
                int readCount;
                byte[] buffer = new byte[1024];

                while ((readCount = bis.read(buffer)) > 0) {
             //       System.out.println("Writing - " + readCount);
                    bos.write(buffer, 0, readCount);
                }
                bis.close();
                bos.close();


                System.out.println("Kopiuje plik " + filename + " ze sciezki " + filepath + "ze zdalnej" + remotepath);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSchException e) {
                e.printStackTrace();
            } catch (SftpException e) {
                e.printStackTrace();
            }
        }

    }

    public JSshConnection(String h, String u, String pa, String po,ProgressBar b) {
        this.host = h;
        this.user = u;
        this.password = pa;
        this.port = po;
        bar = b;
        //start();
    }

    public void connect() {

        try {

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            jsChannel = new JSch();
            session = jsChannel.getSession(user, host);
            session.setPassword(password);
            session.setConfig(config);
            session.setPort(Integer.parseInt(port));
            session.connect();
            System.out.println("Connected");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Mamy blad");
        }



    }

    public void sendCommand(String command) {
        try {
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    //ystem.out.print(new String(tmp, 0, i));

                }



                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }

            System.out.println("DONE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getListOfFiles(String filepath) {
        pliki = new ArrayList<>();
        try {
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("find " + filepath + " -maxdepth 1 -type f -printf \"%f\\n\"");
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    String t = new String(tmp, 0, i);
                    String [] splitted = t.split("\n");
                    // pliki = (ArrayList<String>) Arrays.asList(splitted);
                    for (String text: splitted)
                    {

                        pliki.add(text);
                    }
                    if (i < 0) break;
                    //pliki.add(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }

            System.out.println("DONE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void disconnect() {
        channel.disconnect();
        session.disconnect();
    }

    private boolean isSessionAndChannelOpen() {
        if ((session != null) || (!session.isConnected())) {
            System.out.println("Session exit-status");
            return false;
        } else if (channel.isClosed()) {
            System.out.println("Channel exit-status: " + channel.getExitStatus());
            return false;
        } else {
            return true;
        }

    }

    public void downloadFile(String filepath, String remotepath) {
        // if (isSessionAndChannelOpen()) {

        this.filepath = filepath;
        this.remotepath = remotepath;
        // }
        // }
    }


    public void downloadEveryFileFromDestination(String remotepath) {
        boolean success = false;
        try {

            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(remotepath);
            Vector<ChannelSftp.LsEntry> list = channelSftp.ls("*.*");
            for(ChannelSftp.LsEntry listEntry: list)
            {
                System.out.println("filename " + listEntry.getFilename());
            }

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }

        // }
    }

    public void uploadFile(String source, String destination, String targetDirectory) {
        boolean success = false;
        try {
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(targetDirectory);

            // Validating if files exist to process the request further
            channelSftp.get(source, destination);
        } catch (Exception ee) {
            System.out.println("Exception in download file");
        }
    }

}