package org.roda.rodain.creation.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.roda.rodain.core.I18n;
import org.roda.rodain.creation.SipTypes;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Andre Pereira apereira@keep.pt
 * @since 19/11/2015.
 */
public class CreationModalPreparation extends BorderPane {
  private CreationModalStage stage;

  private static Path outputFolder;
  private ComboBox<String> sipTypes;
  private static Button start;

  /**
   * Creates a modal to prepare for the SIP exportation.
   * <p/>
   * <p>
   * This class creates a pane with a field to choose what the output directory
   * for the SIP exportation should be and the format of the SIPs.
   * </p>
   *
   * @param stage
   *          The stage of the pane
   */
  public CreationModalPreparation(CreationModalStage stage) {
    this.stage = stage;

    getStyleClass().add("sipcreator");

    createTop();
    createCenter();
    createBottom();
  }

  private void createTop() {
    VBox top = new VBox(5);
    top.getStyleClass().add("hbox");
    top.setPadding(new Insets(10, 10, 10, 0));
    top.setAlignment(Pos.CENTER);

    Label title = new Label(I18n.t("CreationModalPreparation.creatingSips"));
    title.setId("title");

    top.getChildren().add(title);
    setTop(top);
  }

  private void createCenter() {
    VBox center = new VBox(5);
    center.setAlignment(Pos.CENTER_LEFT);
    center.setPadding(new Insets(0, 10, 10, 10));

    HBox outputFolderBox = createOutputFolder();
    HBox sipTypesBox = createSipTypes();

    center.getChildren().addAll(outputFolderBox, sipTypesBox);
    setCenter(center);
  }

  private HBox createOutputFolder() {
    HBox outputFolderBox = new HBox(5);
    outputFolderBox.setAlignment(Pos.CENTER_LEFT);
    HBox space = new HBox();
    HBox.setHgrow(space, Priority.ALWAYS);

    Label outputFolderLabel = new Label(I18n.t("CreationModalPreparation.outputDirectory"));
    Button chooseFile = new Button(I18n.t("CreationModalPreparation.choose"));
    chooseFile.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(I18n.t("directorychooser.title"));
        File selectedFile = chooser.showDialog(stage);
        if (selectedFile == null)
          return;
        outputFolder = selectedFile.toPath();
        chooseFile.setText(selectedFile.toPath().getFileName().toString());
        start.setDisable(false);
      }
    });

    outputFolderBox.getChildren().addAll(outputFolderLabel, space, chooseFile);
    return outputFolderBox;
  }

  /**
   * Sets the output folder of the SIPs. Used for testing.
   *
   * @param out
   *          The path of the output folder
   */
  public static void setOutputFolder(String out) {
    if (out != null && !"".equals(out)) {
      outputFolder = Paths.get(out);
      start.setDisable(false);
    }
  }

  private HBox createSipTypes() {
    HBox sipTypesBox = new HBox(5);
    sipTypesBox.setAlignment(Pos.CENTER_LEFT);
    HBox space = new HBox();
    HBox.setHgrow(space, Priority.ALWAYS);

    Label sipTypesLabel = new Label(I18n.t("CreationModalPreparation.sipFormat"));

    sipTypes = new ComboBox<>();
    sipTypes.setId("sipTypes");
    sipTypes.getItems().addAll("BagIt", "E-ARK");
    sipTypes.getSelectionModel().select("E-ARK");

    sipTypesBox.getChildren().addAll(sipTypesLabel, space, sipTypes);
    return sipTypesBox;
  }

  private void createBottom() {
    HBox bottom = new HBox();
    bottom.setPadding(new Insets(0, 10, 10, 10));
    bottom.setAlignment(Pos.CENTER_LEFT);

    HBox space = new HBox();
    HBox.setHgrow(space, Priority.ALWAYS);

    Button cancel = new Button(I18n.t("cancel"));
    cancel.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        stage.close();
      }
    });

    start = new Button(I18n.t("start"));
    start.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        if (outputFolder == null)
          return;
        String selectedType = sipTypes.getSelectionModel().getSelectedItem();
        SipTypes type;
        if ("BagIt".equals(selectedType))
          type = SipTypes.BAGIT;
        else
          type = SipTypes.EARK;

        stage.startCreation(outputFolder, type);
      }
    });

    start.setDisable(true);

    bottom.getChildren().addAll(cancel, space, start);
    setBottom(bottom);
  }
}