package rules;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Observable;

import org.slf4j.LoggerFactory;

import schema.SipPreview;
import utils.TreeWalkHandler;

/**
 * Created by adrapereira on 05-10-2015.
 */
public class SipPerFileHandler extends Observable implements TreeWalkHandler {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SipPerFileHandler.class.getName());
    private final int UPDATEFREQUENCY = 500; //in milliseconds
    private String startPath;
    private ArrayList<SipPreview> sips;
    private int added = 0, returned = 0;
    private long lastUIUpdate = 0;

    public SipPerFileHandler(String startPath){
        this.startPath = startPath;
        sips = new ArrayList<SipPreview>();
    }

    public ArrayList<SipPreview> getSips() {return sips;}
    public int getCount(){return added;}
    public SipPreview getNext(){return sips.get(returned++);}
    public boolean hasNext(){return returned < added;}

    public void preVisitDirectory(Path path, BasicFileAttributes attrs) {    }

    public void postVisitDirectory(Path path) {    }

    public void visitFile(Path path, BasicFileAttributes attrs) {
        String name = "sip_" + path.getFileName().toString();
        TreeNode files = new TreeNode(path.toString());
        sips.add(new SipPreview(name, path.toString(), files));
        added ++;

        long now = System.currentTimeMillis();
        if(now - lastUIUpdate > UPDATEFREQUENCY) {
            setChanged();
            notifyObservers();
            lastUIUpdate = now;
        }
    }

    public void visitFileFailed(Path path) {    }

    public void end() {
        setChanged();
        notifyObservers();
    }
}
