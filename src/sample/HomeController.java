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
    public MenuItem newDirectoryMenuItem;
    public MenuItem newFileMenuItem;
    public MenuItem copyMenuItem;
    public MenuItem moveMenuItem;
    public MenuItem renameMenuItem;
    public MenuItem deleteMenuItem;
    public MenuItem detailsMenuItem;
    public MenuItem aboutMenuItem;
    public MenuItem sourceMenuItem;

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
                File curFile = new File(temp+"\\"+leftListView.getSelectionModel().getSelectedItem().toString());
                if(curFile.isDirectory()){
                    leftDirectory.setText( temp+separator+ leftListView.getSelectionModel().getSelectedItem().toString());
                    exploreDirectory(leftDirectory.getText(),leftChildrenList);
                } else openFile(curFile);
            }
        });
        rightListView.setOnMouseClicked(m -> {
            String temp =rightDirectory.getText().toString();
            String separator = "\\";

            if(temp.endsWith("\\")) separator="";
            if (m.getButton().equals(MouseButton.PRIMARY) && m.getClickCount() == 2){
                File curFile = new File(temp+"\\"+rightListView.getSelectionModel().getSelectedItem().toString());
                if(curFile.isDirectory()) {
                    rightDirectory.setText(temp + separator + rightListView.getSelectionModel().getSelectedItem().toString());
                    exploreDirectory(rightDirectory.getText(),rightChildrenList);
                } else openFile(curFile);
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

            @Override
            public void handle(ActionEvent event) {

                if(leftListView.isFocused()){

                    String temp =leftDirectory.getText().toString();
                    String separator = "\\";
                    File curFile = new File(temp+"\\"+leftListView.getSelectionModel().getSelectedItem().toString());
                    if(temp.endsWith("\\")) separator="";

                    if(curFile.isDirectory()){
                        leftDirectory.setText( temp+separator+ leftListView.getSelectionModel().getSelectedItem().toString());
                        exploreDirectory(leftDirectory.getText(),leftChildrenList);
                    } else openFile(curFile);

                }

                else if(rightListView.isFocused()){

                    String temp =rightDirectory.getText().toString();
                    String separator = "\\";
                    File curFile = new File(temp+"\\"+rightListView.getSelectionModel().getSelectedItem().toString());
                    if(temp.endsWith("\\")) separator="";

                    if(curFile.isDirectory()) {
                        rightDirectory.setText(temp + separator + rightListView.getSelectionModel().getSelectedItem().toString());
                        exploreDirectory(rightDirectory.getText(),rightChildrenList);
                    } else openFile(curFile);

                }
            }
        });

        rightListView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER) && !rightListView.getSelectionModel().isEmpty()) {
                    String separator = "\\";
                    if(rightDirectory.getText().toString().endsWith("\\")) separator = "";
                    String newDirectory = rightDirectory.getText().toString()+separator+rightListView.getSelectionModel().getSelectedItem().toString();
                    exploreDirectory(newDirectory,rightChildrenList);

                    if(new File(newDirectory).isDirectory())
                        rightDirectory.setText(newDirectory);

                    else
                        openFile(new File(newDirectory));

                }
                else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                    int lastIndex = rightDirectory.getText().toString().lastIndexOf("\\");
                    if(lastIndex!=-1){
                        String previousDirectory = rightDirectory.getText().toString().substring(0,lastIndex);
                        String rDir = previousDirectory;
                        if(rDir.endsWith(":")) rDir+="\\";
                        rightDirectory.setText(rDir);
                        previousDirectory=rDir;
                        exploreDirectory(previousDirectory,rightChildrenList);
                        rightDirectory.setText(previousDirectory);
                    }
                }
            }
        });

        leftListView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER) && !leftListView.getSelectionModel().isEmpty()) {
                    String separator = "\\";
                    if(leftDirectory.getText().toString().endsWith("\\")) separator = "";
                    String newDirectory = leftDirectory.getText().toString()+separator+leftListView.getSelectionModel().getSelectedItem().toString();
                    exploreDirectory(newDirectory,leftChildrenList);

                    if(new File(newDirectory).isDirectory())
                        leftDirectory.setText(newDirectory);

                    else
                        openFile(new File(newDirectory));

                }
                else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                    int lastIndex = leftDirectory.getText().toString().lastIndexOf("\\");
                    if(lastIndex!=-1){
                        String previousDirectory = leftDirectory.getText().toString().substring(0,lastIndex);
                        String rDir = previousDirectory;
                        if(rDir.endsWith(":")) rDir+="\\";
                        leftDirectory.setText(rDir);
                        previousDirectory=rDir;
                        exploreDirectory(previousDirectory,leftChildrenList);
                        leftDirectory.setText(previousDirectory);
                    }
                }
            }
        });

        newDirectoryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String ref = "NO_DEFINED_REF";
                if(leftDirectory.isFocused() || leftListView.isFocused()) ref = "LEFT_REF";
                else if(rightListView.isFocused()|| rightDirectory.isFocused()) ref = "RIGHT_REF";
                if(!ref.equals("NO_DEFINED_REF"))
                    FileOperations.createDirectory(ref,leftDirectory.getText().toString(),rightDirectory.getText().toString());
            }
        });

        newFileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String ref = "NO_DEFINED_REF";
                if(leftDirectory.isFocused() || leftListView.isFocused()) ref = "LEFT_REF";
                else if(rightListView.isFocused()|| rightDirectory.isFocused()) ref = "RIGHT_REF";
                if(!ref.equals("NO_DEFINED_REF"))
                    FileOperations.createFile(ref,leftDirectory.getText().toString(),rightDirectory.getText().toString());
            }
        });

        renameMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String ref = "NO_DEFINED_REF";
                String fileRef = "NO_DEFINED_REF";
                if(leftListView.isFocused() ){
                    ref = "LEFT_REF";  fileRef = leftListView.getSelectionModel().getSelectedItem().toString();
                }
                else if(rightListView.isFocused()){
                    ref = "RIGHT_REF"; fileRef = rightListView.getSelectionModel().getSelectedItem().toString();
                }
                if(!ref.equals("NO_DEFINED_REF"))
                    FileOperations.renameFile(ref,fileRef,leftDirectory.getText().toString(),rightDirectory.getText().toString());
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
        } else if(repo.isFile()){

            openFile(repo);
        }
    }
    private void openFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            mDirectory = file;
        } else if (file.isFile()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
            }
        }
    }
}
