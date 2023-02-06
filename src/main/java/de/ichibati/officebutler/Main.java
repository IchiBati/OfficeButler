package de.ichibati.officebutler;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private File file;
    private Invoices invoices;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        GridPane root = new GridPane();
        root.setHgap(20);
        root.setPadding(new Insets(10, 10 ,10 ,10));

        final FileChooser fileChooser = new FileChooser();
        final File pdf;


        VBox vBox = new VBox();
        vBox.setPrefWidth(210);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setMaxWidth(Double.MAX_VALUE);

        Label statusLabel = new Label();

        TextField urlField = new TextField();
        urlField.setPromptText("Press Open... to load PDF");


        TableView<Employee> tableView = new TableView<>();

        Button openButton = new Button("Open...");
        openButton.setMinWidth(60);





        Button convertButton = new Button("Convert");
        convertButton.setPrefWidth(160);
        convertButton.setDisable(true);







        hBox.getChildren().addAll(openButton, convertButton);
        vBox.getChildren().addAll(statusLabel, urlField, hBox, progressBar);

        root.add(vBox, 0, 0);
        root.add(tableView, 1, 0);



        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statusLabel.setText("");
                file = fileChooser.showOpenDialog(stage);

                if(file != null){
                    try {
                        invoices = new Invoices(file);
                        convertButton.setDisable(false);
                        urlField.setText(file.getName());


                    } catch (IOException e) {
                        statusLabel.setText("Error: Open file failed");
                        convertButton.setDisable(true);
                    }
                }

            }
        });


        convertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                convertButton.setDisable(true);
                openButton.setDisable(true);
                try {
                    for(PDDocument files : invoices.splitInvoices()){
                        EmployeeFactory.createEmployee(files);
                    }

                    SharedEmployeeDatabase.getInstance().getDatabase().forEach(Employee::invoicesToFile);
                    statusLabel.setText("Converting succeed!");

                } catch (IOException e) {
                    statusLabel.setText("Error: Converting failed");

                }finally {
                    convertButton.setDisable(false);
                    convertButton.setDisable(false);
                }
            }
        });












        Scene scene = new Scene(root);
        stage.setTitle("OfficeButler");
        stage.sizeToScene();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}