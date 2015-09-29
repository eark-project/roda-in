package rules.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import core.Footer;
import core.Main;

/**
 * Created by adrapereira on 24-09-2015.
 */
public class RulesPane extends BorderPane {
    private HBox createRule;
    private ListView<Rule> listView;

    public RulesPane(Stage stage){
        createCreateRule();
        createListView();

        this.setTop(createRule);
        this.setCenter(listView);
        this.minWidthProperty().bind(stage.widthProperty().multiply(0.2));
    }

    private void createCreateRule(){
        Button btn = new Button("Create Rule");
        Label title = new Label("Mapping Rules");

        HBox space = new HBox();
        HBox.setHgrow(space, Priority.ALWAYS);

        createRule = new HBox();
        createRule.setPadding(new Insets(10, 10, 10, 10));
        createRule.setSpacing(10);
        createRule.setAlignment(Pos.TOP_RIGHT);
        createRule.getChildren().addAll(title, space, btn);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String source = Main.getSourceSelectedItem();
                String schema = Main.getSchemaSelectedItem();
                Footer.setStatus("Carregou no \"Create Rule\": " + source + " <-> " + schema);
            }
        });
    }

    private void createListView(){
        listView = new ListView<Rule>();
        listView.getItems().add(new Rule());
    }
}
