package sample;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {
    ContextMenu cm;
    Viewer viewer;

    @Override
    public void start(Stage primaryStage) throws Exception{

        ScrollPane root=new ScrollPane();
        root.setPannable(true);

        primaryStage.setTitle("Image Viewer");
        Scene sc;
        primaryStage.setScene(sc=new Scene(root, 300, 275));
        primaryStage.show();

        guiInit(root,primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void guiInit(ScrollPane root,Stage primaryStage){
        viewer=new Viewer(root);



        cm=new ContextMenu();
        MenuItem openMenu=new MenuItem("Open Folder");
        openMenu.setOnAction(ae->{
            DirectoryChooser dc=new DirectoryChooser();
            try {
                viewer.init(dc.showDialog(primaryStage.getOwner()));
            }catch(Exception ex){
                System.err.println(ex.getMessage());
            }

        });
        cm.getItems().add(openMenu);

        root.setOnMouseClicked(me->{
            if(me.getButton().equals(MouseButton.PRIMARY)){
                if(me.getClickCount()==2){
                    primaryStage.setFullScreen(!primaryStage.isFullScreen());
                }
            }else if(me.getButton().equals(MouseButton.SECONDARY)){
                cm.show(root,me.getScreenX(),me.getScreenY());
            }
        });
    }
}
