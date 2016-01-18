package org.roda.rodain.schema.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * @author Andre Pereira apereira@keep.pt
 * @since 12-10-2015.
 */
public class SchemaTreeCell extends TreeCell<String> {
  /**
   * Creates a new SchemaTreeCell
   */
  public SchemaTreeCell() {
    super();
  }

  @Override
  public void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (getStyleClass().contains("schemaNodeEmpty"))
      getStyleClass().remove("schemaNodeEmpty");

    if (empty || item == null) {
      setText(null);
      setGraphic(null);
      // To hide the hover color in the empty nodes
      if (getStyleClass().contains("schemaNode"))
        getStyleClass().remove("schemaNode");
      if (getStyleClass().contains("tree-cell"))
        getStyleClass().remove("tree-cell");
    } else {
      if (!getStyleClass().contains("tree-cell"))
        getStyleClass().add("tree-cell");
      if (!getStyleClass().contains("schemaNode"))
        getStyleClass().add("schemaNode");

      HBox hbox = new HBox();
      hbox.setAlignment(Pos.BOTTOM_LEFT);
      Label lab = new Label(item);
      lab.getStyleClass().add("cellText");
      Image icon = null;

      // Get the correct item
      TreeItem<String> treeItem = getTreeItem();
      if (treeItem == null)
        return;

      boolean addHbox = false;
      if (treeItem instanceof SchemaNode) {
        setEditable(true);
        SchemaNode itemNode = (SchemaNode) treeItem;
        icon = itemNode.getImage();
        updateDObj(item);
        addHbox = true;
      } else {
        if (treeItem instanceof SipPreviewNode) {
          setEditable(false);
          addHbox = true;
          SipPreviewNode sipNode = (SipPreviewNode) treeItem;
          icon = sipNode.getIcon();
          if (sipNode.isMetaModified() || sipNode.isContentModified()) {
            setText("*");
          } else
            setText("");
        }
      }
      if (addHbox) {
        hbox.getChildren().addAll(new ImageView(icon), lab);
        setGraphic(hbox);
      }
    }
  }

  private void updateDObj(String title) {
    TreeItem<String> item = getTreeItem();
    if (item instanceof SchemaNode) {
      SchemaNode node = (SchemaNode) item;
      node.getDob().setTitle(title);
    }
  }
}
