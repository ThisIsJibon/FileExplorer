package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends Application implements Initializable {

    //UI elements declaration
    public TextField leftDirectory;
    public TextField rightDirectory;
    public ListView leftListView;
    public ListView rightListView;
    public MenuItem exploreMenuItem;

    //other declarations
    private File mDirectory;
    ObservableList<String> leftChildrenList;
    ObservableList<String> rightChildrenList;


    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeDirectoryPath();

        leftListView.setOnMouseClicked(m -> {
            String temp =leftDirectory.getText().toString();
            String separator = "\\";
            if(temp.endsWith("\\")) separator="";
            if (m.getButton().equals(MouseButton.PRIMARY) && m.getClickCount() == 2){
                leftDirectory.setText( temp+separator+ leftListView.getSelectionModel().getSelectedItem().toString());
                exploreDirectory(leftDirectory.getText(),leftChildrenList);
            }

        });
        rightListView.setOnMouseClicked(m -> {
            String temp =rightDirectory.getText().toString();
            String separator = "\\";
            if(temp.endsWith("\\")) separator="";
            if (m.getButton().equals(MouseButton.PRIMARY) && m.getClickCount() == 2){
                rightDirectory.setText( temp+separator+ rightListView.getSelectionModel().getSelectedItem().toString());
                exploreDirectory(rightDirectory.getText(),rightChildrenList);
            }
        });
        rightDirectory.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    exploreDirectory(rightDirectory.getText().toString(),rightChildrenList);
                }
            }
        });
        leftDirectory.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    exploreDirectory(leftDirectory.getText().toString(),leftChildrenList);
                }
            }
        });
        exploreMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            String temp =leftDirectory.getText().toString();
            String separator = "\\";

            @Override
            public void handle(ActionEvent event) {
                if(leftListView.isFocused()){
                    temp =leftDirectory.getText().toString();
                    separator = "\\";
                    if(temp.endsWith("\\")) separator="";
                    leftDirectory.setText( temp+separator+ leftListView.getSelectionModel().getSelectedItem().toString());
                    exploreDirectory(leftDirectory.getText(),leftChildrenList);
                } else if(rightListView.isFocused()){
                    temp = rightDirectory.getText().toString();
                    separator = "\\";
                    if(temp.endsWith("\\")) separator="";
                    rightDirectory.setText( temp+separator+ rightListView.getSelectionModel().getSelectedItem().toString());
                    exploreDirectory(rightDirectory.getText(),rightChildrenList);
                }
            }
        });
    }

    private void initializeDirectoryPath(){
        File[] roots = File.listRoots();
        String  rightPanePath =  roots[0].getPath();
        String leftPanePath = roots.length > 1 ? roots[1].getPath() : rightPanePath;
        //leftPanePath+="\\";
        leftDirectory.setText(leftPanePath);
        rightDirectory.setText(rightPanePath);

        initializeFileList(leftPanePath,rightPanePath);
    }
    private void initializeFileList(String leftPanePath, String rightPanePath){

        leftChildrenList = FXCollections.observableArrayList();
        rightChildrenList = FXCollections.observableArrayList();

        leftListView.setItems(leftChildrenList);
        rightListView.setItems(rightChildrenList);

        exploreDirectory(leftPanePath,leftChildrenList);
        exploreDirectory(rightPanePath,rightChildrenList);


    }
    private void exploreDirectory (String filepath,ObservableList<String> curChildList){
        File repo = new File(filepath);
        if (repo.isDirectory()) {
            File[] fileList = repo.listFiles(file -> !file.isHidden());
            String[] list = new String[fileList.length];
            for (int i = 0; i < list.length; ++i) {
                list[i] = fileList[i].getName();
            }
            if (list != null) {
                curChildList.clear();
                curChildList.addAll(list);
            } else {
                curChildList.clear();
            }
        }
    }
}
