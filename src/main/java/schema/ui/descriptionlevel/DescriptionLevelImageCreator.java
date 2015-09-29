package schema.ui.descriptionlevel;

import java.io.InputStream;

import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Created by adrapereira on 22-09-2015.
 */
public class DescriptionLevelImageCreator {
    String unicode;
    final double size = 16;

    public DescriptionLevelImageCreator(String unicode) {
        this.unicode = unicode;
    }

    public Image generate(){
        InputStream fontIS = getClass().getResourceAsStream("/fontawesome-webfont.ttf");
        Font font = Font.loadFont(fontIS, 16);

        final Canvas canvas = new Canvas(size, size);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(font);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(unicode, size / 2, size / 2);

        final SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        final WritableImage snapshot = canvas.snapshot(params, null);
        return snapshot;
    }
}
