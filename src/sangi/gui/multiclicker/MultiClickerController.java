package sangi.gui.multiclicker;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import sangi.gui.controls.HotkeyButton;
import sangi.gui.controls.IntegerSpinner;

import java.awt.*;
import java.util.List;

public class MultiClickerController {

    // FXML Controls
    public TableView<Point> pointTableView;
    public TableColumn<Point, String> pointColumn;
    public IntegerSpinner secondsPPDelaySpinner;
    public IntegerSpinner millisPPDelaySpinner;
    public IntegerSpinner secondsClickDelaySpinner;
    public IntegerSpinner millisClickDelaySpinner;
    public ToggleGroup mouseMovementGroup;
    public Toggle linearToggle;
    public HotkeyButton startKeyButton;
    public HotkeyButton stopKeyButton;
    public HotkeyButton savePointKeyButton;
    public HotkeyButton deletePointKeyButton;

    @FXML
    public void initialize(){
        pointColumn.setCellValueFactory(param -> {
            Point point = param.getValue();
            String pointToString = "(" + point.x + " , " + point.y + ")";
            return new SimpleStringProperty(pointToString);
        });
    }

    /******  Button Methods *****/
    public void setStartKey(KeyCode newKey){
        boolean keyNotUsed = !(newKey.equals(stopKeyButton.getKey()) ||
                               newKey.equals(savePointKeyButton.getKey()) ||
                               newKey.equals(deletePointKeyButton.getKey()));

        if (keyNotUsed){
            startKeyButton.setKey(newKey);
        }
    }

    public void setStopKey(KeyCode newKey){
        boolean keyNotUsed = !(newKey.equals(startKeyButton.getKey()) ||
                newKey.equals(savePointKeyButton.getKey()) ||
                newKey.equals(deletePointKeyButton.getKey()));

        if (keyNotUsed){
            stopKeyButton.setKey(newKey);
        }

    }

    public void setSavePointKey(KeyCode newKey){
        boolean keyNotUsed = !(newKey.equals(stopKeyButton.getKey()) ||
                newKey.equals(startKeyButton.getKey()) ||
                newKey.equals(deletePointKeyButton.getKey()));

        if (keyNotUsed){
            savePointKeyButton.setKey(newKey);
        }
    }

    public void setDeleteLastPointKey(KeyCode newKey){
        boolean keyNotUsed = !(newKey.equals(stopKeyButton.getKey()) ||
                newKey.equals(savePointKeyButton.getKey()) ||
                newKey.equals(startKeyButton.getKey()));

        if (keyNotUsed){
            deletePointKeyButton.setKey(newKey);
        }
    }

    public KeyCode getStartKey(){
        return startKeyButton.getKey();
    }

    public KeyCode getStopKey(){
        return stopKeyButton.getKey();
    }

    public KeyCode getSavePointKey(){
        return savePointKeyButton.getKey();
    }

    public KeyCode getDeleteLastPointKey() {
        return deletePointKeyButton.getKey();
    }

    public boolean isStartButtonRecording(){
        return startKeyButton.isRecording();
    }

    public boolean isStopButtonRecording(){
        return stopKeyButton.isRecording();
    }

    public boolean isSaveButtonRecording() { return savePointKeyButton.isRecording(); }

    public boolean isDeleteButtonRecording() { return deletePointKeyButton.isRecording(); }


    /******  Table Methods *****/

    public void addNewPoint(Point point){
        pointTableView.getItems().add(point);
    }

    public void removeLastPoint(){
        ObservableList<Point> points = pointTableView.getItems();
        int size = points.size();
        if (size > 0){
            points.remove(points.size() - 1);
        }
    }

    public List<Point> getPoints(){
        return pointTableView.getItems();
    }


    /******  Delay Methods *****/

    public int getPPSeconds(){
        return secondsPPDelaySpinner.getValue();
    }

    public int getPPMillis(){
        return millisPPDelaySpinner.getValue();
    }

    public int getClickSeconds(){
        return secondsClickDelaySpinner.getValue();
    }

    public int getClickMillis(){
        return millisClickDelaySpinner.getValue();
    }

    /******  Mouse Movement Methods *****/

    public boolean isLinear(){
        return mouseMovementGroup.getSelectedToggle().equals(linearToggle);
    }



}
