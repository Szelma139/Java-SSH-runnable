package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.text.Keymap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class interfaceController {

    @FXML
    private Button downloadButton;

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Button editFilesButton;

    @FXML
    private Label targetDirectory;

    @FXML
    private Label remoteDirectory;

    @FXML
    private ProgressBar bar;

    @FXML
    private ListView log;

    @FXML
    private Label progressPercentage;

    private JSshConnection js = null;

    ArrayList<String> listaPlikow = null;

    ArrayList<Tab> listaTabow = null;

    boolean filesDownloaded = false;


    public void initialize()
    {
ipField.setText("127.0.0.1");
portField.setText("22");
    }


    public void downloadFiles(ActionEvent actionEvent) throws IOException {

        DirectoryHelper d = new DirectoryHelper();
        d.setUpStuff();
        targetDirectory.setText(MainSettings.getLocalPath());
        remoteDirectory.setText(MainSettings.getRemotePath());

        listaPlikow = new ArrayList<>();
        System.out.println("Hello World!");

        try {

            d.createFolder();
        } catch (IOException e) {
            e.printStackTrace();
        }


        js = new JSshConnection(ipField.getText(), MainSettings.getUser(), MainSettings.getPassword(), portField.getText(), bar);
        js.connect();
        String pathToFiles = MainSettings.getLocalPath();
        String remotePath = MainSettings.getRemotePath();

        js.sendCommand("find " + remotePath + " -maxdepth 1 -type f -printf \"%f\\n\"");
        js.getListOfFiles(remotePath);

        try {
            js.downloadFile(d.getDownloadFilesDirectory(), remotePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        js.start();

    }

    public void editFiles(ActionEvent actionEvent) throws IOException {

      //  if (js.getFilesDownloadedStatus()) {

            List<FileStructure> list = new ArrayList<>();
            for (int i = 0; i < js.getPliki().size(); i++) {
                Parser p = new Parser();
             //   p.getListOfKeyValuePairs(MainSettings.getLocalPath(), js.getPliki().get(i));
                //list = p.getListOfKeyPairValues(MainSettings.getLocalPath(), js.getPliki().get(i));
                list.add(p.getListOfKeyPairValues(MainSettings.getLocalPath(),js.getPliki().get(i)));
            }

            /*
            for (FileStructure element:list
                 ) {
                System.out.println("Filename - " + element.getFilename() + "\n");
                System.out.println("Groupname -" + element.getGroupname() + "\n");
                        for (int i = 0; i < element.getKeyValuePairs().size(); i++)
                {
                    System.out.println("Key - " + element.getKeyValuePairs().get(i).getKey() + "\n");
                    System.out.println("Value - " + element.getKeyValuePairs().get(i).getValue() + "\n");


                }

            }
            */
      //  }
//stworzyc nowy interfejs na podstawie listy TUTAJ
    }



}
