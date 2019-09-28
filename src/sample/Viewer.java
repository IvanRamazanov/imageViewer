package sample;


import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.ArrayList;

public class Viewer {
    ArrayList<File> fileList;
    ImageView viewer;
    int iterator=0;
    ScrollPane root;
    double currentImgWidth,currentImgHeight;

    Viewer(ScrollPane root){
        viewer=new ImageView();
        viewer.setPreserveRatio(true);
        fileList=new ArrayList<>();

        this.root=root;
        root.setContent(viewer);

        root.addEventFilter(ScrollEvent.ANY,se->{
            if(se.isControlDown()){
                if(se.getDeltaY()>0){
                    /*viewer.setScaleX(viewer.getScaleX()+0.1);
                    viewer.setScaleY(viewer.getScaleY()+0.1);*/
                    addSize();
                }else{
                    /*viewer.setScaleX(viewer.getScaleX()-0.1);
                    viewer.setScaleY(viewer.getScaleY()-0.1);*/
                    reduceSize();
                }
                se.consume();
            }
        });

        root.setOnKeyReleased(ke->{
            if(ke.getCode().equals(KeyCode.RIGHT) || ke.getCode().equals(KeyCode.D)){
                try{
                    iterator++;
                    updateImage();
                }catch (Exception ex){

                }
            }else if(ke.getCode().equals(KeyCode.LEFT) || ke.getCode().equals(KeyCode.A)){
                try{
                    iterator--;
                    updateImage();
                }catch (Exception ex){

                }
            }else if(ke.getCode().equals(KeyCode.PLUS)){
                viewer.setScaleX(viewer.getScaleX()+0.1);
                viewer.setScaleY(viewer.getScaleY()+0.1);
            }
        });
    }

    void init(File folder) throws Exception{
        if(folder!=null) {
            File[] files = folder.listFiles();
            for (int i=0;i<files.length;i++){
                File f=files[i];
                if(f.getName().endsWith(".jpg")||f.getName().endsWith(".png")){
                    fileList.add(f);
                }
            }

            iterator=0;
            updateImage();
        }
    }

    void updateImage() throws Exception{
        if(iterator>=0 && iterator<fileList.size()) {
            Image img=new Image(fileList.get(iterator).toURI().toURL().toString());
            currentImgHeight=img.getHeight();
            currentImgWidth=img.getWidth();
            viewer.setImage(img);
            viewer.setFitHeight(currentImgHeight);
            viewer.setFitHeight(currentImgHeight);

            //root.setHvalue(0);
            //root.setVvalue(0);
        }
    }

    void reduceSize(){
        currentImgHeight*=95.0/100.0;
        viewer.setFitHeight(currentImgHeight);
    }
    void addSize(){
        currentImgHeight*=100.0/95.0;
        viewer.setFitHeight(currentImgHeight);
    }
}
