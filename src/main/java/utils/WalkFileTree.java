package utils;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by adrapereira on 01-10-2015.
 */
public class WalkFileTree extends Thread{
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(WalkFileTree.class.getName());
    private String startPath;
    private TreeVisitor handler;

    public WalkFileTree(String startPath, TreeVisitor handler){
        this.startPath = startPath;
        this.handler = handler;
    }

    @Override
    public void run() {
        final Path path = Paths.get(startPath);

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    handler.visitFile(file, attrs);
                    return isTerminated();
                }
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    handler.preVisitDirectory(dir, attrs);
                    return isTerminated();
                }
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    handler.postVisitDirectory(dir);
                    return isTerminated();
                }
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    handler.visitFileFailed(file);
                    return isTerminated();
                }
            });
        } catch (IOException e) {
            log.error("" + e);
        }
        handler.end();
    }

    private FileVisitResult isTerminated(){
        //terminate if the thread has been interrupted
        if (Thread.interrupted()) {
            handler.end();
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }
}
