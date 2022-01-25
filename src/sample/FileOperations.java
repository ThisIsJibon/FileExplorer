package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileOperations {
        public static void createDirectory(String ref,String leftDir,String rightDir){
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Directory Title");
                dialog.setHeaderText("Enter a valid directory title");
                dialog.show();
                Button confirm = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                confirm.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                                String path="";
                                String separator = "\\";
                                if(ref=="LEFT_REF")
                                        path = leftDir;
                                else path = rightDir;
                                if(path.endsWith("\\")) separator="";

                               // System.out.println(path+separator+dialog.getEditor().getText().toString());
                                Path parent = Paths.get(path);
                                Path curDirPath = parent.resolve(dialog.getEditor().getText().toString());
                                try {
                                        Files.createDirectory(curDirPath);
                                } catch (Exception e){
                                        
                                }

                        }
                });
        }
}
