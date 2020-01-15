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
import javafx.scene.layout.VBox;
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
        //Parent root = FXMLLoader.load(getClass().getResource("interface.fxml"));
        //primaryStage.setTitle("Hello World");
        //Text scenetitle = new Text("SSH Lazy Loader");
        //scenetitle.setFont(Font.font("Tahoma"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("interface.fxml"));
        primaryStage.setScene(new Scene((Parent) loader.load()));
        interfaceController controller = loader.getController();

        /*
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

         */
       // Scene scene = new Scene(grid, 500, 375);

       // primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void readFiles() throws IOException {

    }

};


