package actiangent.to.pdf;

import actiangent.to.pdf.converter.DocumentConverter;
import actiangent.to.pdf.repository.DocumentsRepository;
import actiangent.to.pdf.ui.main.DocumentFileScope;
import actiangent.to.pdf.ui.main.MainController;
import actiangent.to.pdf.ui.main.MainViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private final DocumentConverter documentConverter = new DocumentConverter();
    private final DocumentFileScope documentFileScope = new DocumentFileScope();
    private final DocumentsRepository documentsRepository = new DocumentsRepository(documentConverter);
    private final MainViewModel mainViewModel = new MainViewModel(documentsRepository, documentFileScope);
    private final MainController mainController = new MainController(mainViewModel);
    private AnchorPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main_ui.fxml"));
            loader.setController(mainController);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("To PDF");
        primaryStage.show();
    }

}
