package schema.ui;

import java.io.File;
import java.nio.file.Path;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.slf4j.LoggerFactory;

import source.ui.items.SourceTreeItem;

/**
 * Created by adrapereira on 17-09-2015.
 */
public class SipContentDirectory extends TreeItem<Object> implements SourceTreeItem{
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SipContentDirectory.class.getName());
    public static final Image folderCollapseImage = new Image(ClassLoader.getSystemResourceAsStream("icons/folder.png"));
    public static final Image folderExpandImage = new Image(ClassLoader.getSystemResourceAsStream("icons/folder-open.png"));
    //this stores the full path to the file or directory
    private String fullPath;

    public String getPath() {
        return (this.fullPath);
    }

    public SipContentDirectory(Path file) {
        super(file.toString());
        this.fullPath = file.toString();
        this.setGraphic(new ImageView(folderCollapseImage));

        //set the value
        if (!fullPath.endsWith(File.separator)) {
            //set the value (which is what is displayed in the tree)
            String value = file.toString();
            int indexOf = value.lastIndexOf(File.separator);
            if (indexOf > 0) {
                this.setValue(value.substring(indexOf + 1));
            } else {
                this.setValue(value);
            }
        }

        this.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler<TreeModificationEvent<Object>>() {
            public void handle(TreeModificationEvent<Object> e) {
                SipContentDirectory source = (SipContentDirectory) e.getSource();
                if (source.isExpanded()) {
                    ImageView iv = (ImageView) source.getGraphic();
                    iv.setImage(folderExpandImage);
                }
            }
        });

        this.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler<TreeModificationEvent<Object>>() {
            public void handle(TreeModificationEvent<Object> e) {
                SipContentDirectory source = (SipContentDirectory) e.getSource();
                if (!source.isExpanded()) {
                    ImageView iv = (ImageView) source.getGraphic();
                    iv.setImage(folderCollapseImage);
                }
            }
        });
    }
}
