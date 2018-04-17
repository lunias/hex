package com.ethanaa.hex.log;

import com.ethanaa.hex.log.model.LogFile;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogApplication extends Application implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(LogApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(LogApplication.class, args);
        launch(args);
    }

    @Override
    public void run(String... args) throws Exception {

        LOG.info("Welcome to log analyzer");
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Log Analyzer");

        Group root = new Group();
        Scene scene = new Scene(root, 400, 300, Color.WHITE);

        TabPane tabPane = new TabPane();
        BorderPane mainPane = new BorderPane();

        LogFile processorLogFile = new LogFile("processor");
        LogFile targeterLogFile = new LogFile("targeter");
        LogFile searchServiceLogFile = new LogFile("search service");


        Tab processorTab = new Tab();
        processorTab.setText("Processor");
        processorTab.setClosable(false);
        processorTab.setContent(createBrowseButton(processorLogFile));
        tabPane.getTabs().add(processorTab);

        Tab targeterTab = new Tab();
        targeterTab.setText("Targeter");
        targeterTab.setClosable(false);
        targeterTab.setContent(createBrowseButton(targeterLogFile));
        tabPane.getTabs().add(targeterTab);

        Tab searchServiceTab = new Tab();
        searchServiceTab.setText("Search Service");
        searchServiceTab.setClosable(false);
        searchServiceTab.setContent(createBrowseButton(searchServiceLogFile));
        tabPane.getTabs().add(searchServiceTab);

        mainPane.setCenter(tabPane);
        mainPane.prefHeightProperty().bind(scene.heightProperty());
        mainPane.prefWidthProperty().bind(scene.widthProperty());
        root.getChildren().add(mainPane);
        stage.setScene(scene);
        stage.show();
    }

    private Button createBrowseButton(LogFile logFile) {

        return new Button("Browse for log [" + logFile.getName() + "] ...");
    }

}
