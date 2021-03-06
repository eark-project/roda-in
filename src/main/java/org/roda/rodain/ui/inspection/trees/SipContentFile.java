package org.roda.rodain.ui.inspection.trees;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Path;

import org.roda.rodain.core.Constants;

/**
 * @author Andre Pereira apereira@keep.pt
 * @since 17-09-2015.
 */
public class SipContentFile extends TreeItem<Object> implements InspectionTreeItem {
  public static final Image fileImage = new Image(ClassLoader.getSystemResourceAsStream(Constants.RSC_ICON_FILE));
  private Path fullPath;
  private TreeItem parent;

  /**
   * Creates a new TreeItem, representing a file.
   *
   * @param file
   *          The Path that will be associated to the item.
   * @param parent
   *          The item's parent.
   */
  public SipContentFile(Path file, TreeItem parent) {
    super(file.toString());
    this.fullPath = file;
    this.parent = parent;
    this.setGraphic(new ImageView(fileImage));

    Path name = fullPath.getFileName();
    if (name != null) {
      this.setValue(name.toString());
    } else {
      this.setValue(fullPath.toString());
    }
  }

  /**
   * @return This item's parent.
   */
  @Override
  public TreeItem getParentDir() {
    return parent;
  }

  /**
   * Sets the parent directory
   *
   * @param t
   *          the new parent directory
   */
  @Override
  public void setParentDir(TreeItem t) {
    parent = t;
  }

  /**
   * @return The path of this item.
   */
  @Override
  public Path getPath() {
    return this.fullPath;
  }
}
