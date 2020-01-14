package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import javax.swing.text.Keymap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    ProgressBar bar = null;
    ArrayList<String> listaPlikow = null;
    ArrayList<Tab> listaTabow = null;
    JSshConnection js = null;
    TextField ipTextField = null;
    TextField portTextField = null;
    boolean filesDownloaded = false;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        Text scenetitle = new Text("SSH Lazy Loader");
        scenetitle.setFont(Font.font("Tahoma"));
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(scenetitle, 0, 0, 2, 1);

        Label ipLabel = new Label("IP:");
        grid.add(ipLabel, 0, 1);

         ipTextField = new TextField();
        ipTextField.setText("127.0.0.1");
        grid.add(ipTextField, 1, 1);

        Label port = new Label("port:");
        grid.add(port, 0, 2);


         portTextField = new TextField();
        portTextField.setText("22");
        grid.add(portTextField, 1, 2);


        // grid.setPadding(new Insets(25, 25, 25, 25));


        bar = new ProgressBar();
        bar.setProgress(0);
        grid.add(bar, 0, 3,1,5);


        Button downloadButton = new Button();
        downloadButton.setText("Pobierz");
        grid.add(downloadButton, 0, 6);

        setUpStuff();

        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
               processFiles();

            }

        });


        Button readButton = new Button();
        readButton.setText("Czytaj");
        grid.add(readButton, 2, 6,1,4);

        readButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    readFiles();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            });
        Scene scene = new Scene(grid, 500, 375);

        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }



    public void createFolder() throws IOException {
        File file = new File("DownloadedFiles");
        boolean bool = file.mkdir();
        if (bool)
            System.out.println("Stworzono folder");
        else
            System.out.println("Nie udalo sie stworzyc lub folder juz istnieje");
        if (!file.exists())
            return;
    }

    public String getPathdirectory() throws IOException {

        String path = new File(".").getCanonicalPath();
        System.out.println(path + "/");
        return path + "/";
    }

    public String getDownloadFilesDirectory() throws IOException {
        return getPathdirectory() + "DownloadedFiles/";
    }

    public void setUpStuff() throws IOException {
        MainSettings.setUser("radek");
        MainSettings.setPassword("radek2");
        MainSettings.setLocalPath(getDownloadFilesDirectory());
        MainSettings.setRemotePath("/home/radek/Wideo/");
    }
    public void processFiles()
    {
        listaPlikow = new ArrayList<>();
        System.out.println("Hello World!");

        try {
            createFolder();
        } catch (IOException e) {
            e.printStackTrace();
        }


        js = new JSshConnection(ipTextField.getText(), MainSettings.getUser(), MainSettings.getPassword(), portTextField.getText(), bar);
        js.connect();
        String pathToFiles = MainSettings.getLocalPath();
        String remotePath = MainSettings.getRemotePath();

        js.sendCommand("find " + remotePath + " -maxdepth 1 -type f -printf \"%f\\n\"");
        js.getListOfFiles(remotePath);

        try {
            js.downloadFile(getDownloadFilesDirectory(), remotePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        js.start();
        filesDownloaded = true;
    }
    public void readFiles() throws IOException {
        if (filesDownloaded) {
            List<Keymap> list = new ArrayList<>();
            for (int i = 0; i < js.getPliki().size(); i++) {
                Parser p = new Parser();
                p.getListOfKeyValuePairs(MainSettings.getLocalPath(), js.getPliki().get(i));
            }
        }
    }

};


