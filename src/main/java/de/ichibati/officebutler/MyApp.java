package de.ichibati.officebutler;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;

public class MyApp extends Application {

    public static Path inputFile;

    private Invoices invoices;

    private ProgressBar progressBar;

    private final Label statusLabel = new Label("Status");

    Button convertButton = new Button("Convert");

    Button openButton = new Button("Open..");

    private void invokeConvertTask(){
        ConvertTask task = new ConvertTask(inputFile, invoices);
        progressBar.progressProperty().bind(task.progressProperty());
        task.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                statusLabel.setText(newValue);
                openButton.setDisable(false);
                SharedEmployeeDatabase.getInstance().getDatabase().clear();
                progressBar.progressProperty().unbind();
                progressBar.setProgress(0);
            }
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){

        GridPane root = new GridPane();
        root.setHgap(20);
        root.setPadding(new Insets(10, 10 ,10 ,10));

        final FileChooser fileChooser = new FileChooser();


        VBox vBox = new VBox();
        vBox.setPrefWidth(230);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(5);

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);

        progressBar = new ProgressBar(0.0);
        progressBar.setMaxWidth(Double.MAX_VALUE);


        statusLabel.setTextAlignment(TextAlignment.LEFT);

        TextField urlField = new TextField();
        urlField.setPromptText("Press Open... to load PDF");
        urlField.setFocusTraversable(false);


        TableView<Employee> tableView = new TableView<>();

        openButton.setMinWidth(65);

        convertButton.setPrefWidth(160);
        convertButton.setDisable(true);

        hBox.getChildren().addAll(openButton, convertButton);
        vBox.getChildren().addAll(statusLabel, urlField, hBox, progressBar);

        root.add(vBox, 0, 0);
        //root.add(tableView, 1, 0);

        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statusLabel.setText("Status: ");
                File file = fileChooser.showOpenDialog(stage);


                if(file != null){
                    inputFile = file.toPath();
                    try {

                        invoices = new Invoices(inputFile);
                        convertButton.setDisable(false);
                        urlField.setText(inputFile.getFileName().toString());
                        statusLabel.setText("Status: Loading PDF Succed");



                    } catch (NoInvoiceException e) {
                        statusLabel.setText(e.getMessage());
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

                // converting and saving inovices in new Thread
                invokeConvertTask();
            }
        });














        Scene scene = new Scene(root);
        stage.setTitle("OfficeButler");
        stage.sizeToScene();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public  Path getInputFile(){
        return inputFile;
    }
}