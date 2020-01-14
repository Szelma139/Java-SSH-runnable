package sample;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javax.swing.text.Keymap;
import java.util.ArrayList;
import java.util.List;

public class Tab {
    List<Keymap> list = new ArrayList<>();

    public void startNextWindow()
    {
        Button saveButton = new Button();
        saveButton.setText("Zapisz");
        Button sendButton = new Button();
        sendButton.setText("Wyslij");

        GridPane grid = new GridPane();
        grid.add(saveButton, 0, 1);
        grid.add(sendButton, 0, 2);

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("zostalem klikniety");


            }

        });


        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("zostalem klikniety");
            }

        });
    }


}


