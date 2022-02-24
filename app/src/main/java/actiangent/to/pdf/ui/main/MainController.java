package actiangent.to.pdf.ui.main;

import actiangent.to.pdf.model.DocumentFile;
import com.google.common.io.Files;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    private final FileChooser fileChooser = new FileChooser();
    private final List<String> validFileExtensions = new ArrayList<>();
    private final MainViewModel viewModel;

    @FXML
    private AnchorPane root;
    @FXML
    private TableView<DocumentFile> documentFileTableView;
    @FXML
    private Button chooseFileButton;
    @FXML
    private Button convertSelectedDocumentButton;
    @FXML
    private Button convertAllDocumentButton;

    public MainController(MainViewModel viewModel) {
        this.viewModel = viewModel;
        fileChooser.getExtensionFilters().add(viewModel.getExtensionFilter());
        validFileExtensions.addAll(fileChooser.getExtensionFilters().get(0).getExtensions()
                .stream().map(extension -> extension.replaceAll("\\*\\.", ""))
                .collect(Collectors.toList()));
    }

    private void setupDocumentFileTable() {
        SortedList<DocumentFile> documentFileSortedList = new SortedList<>(viewModel.getDocumentFilesList());
        documentFileSortedList.comparatorProperty().bind(documentFileTableView.comparatorProperty());
        documentFileTableView.setItems(documentFileSortedList);

        viewModel.selectedDocumentFileProperty()
                .bind(documentFileTableView.getSelectionModel().selectedItemProperty());
    }

    private void setupDocumentFileTableDragDrop() {
        documentFileTableView.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                if (!validFileExtensions.containsAll(
                        event.getDragboard().getFiles()
                                .stream().map(file -> Files.getFileExtension(file.getPath()))
                                .collect(Collectors.toList()))
                ) {
                    event.consume();
                    return;
                }

                event.acceptTransferModes(TransferMode.ANY);
            }
        });

        documentFileTableView.setOnDragDropped(event -> {
            if (event.getDragboard().hasFiles()) {
                event.getDragboard().getFiles()
                        .forEach(file -> viewModel.addDocumentFilesToList(new DocumentFile(file)));
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }

    public void selectDocumentAction(ActionEvent actionEvent) {
        List<File> fileList = fileChooser
                .showOpenMultipleDialog(root.getScene().getWindow());

        for (File file : fileList) {
            viewModel.addDocumentFilesToList(new DocumentFile(file));
        }
    }

    public void convertDocumentAction(ActionEvent actionEvent) {
        viewModel.convertToPdf();
    }

    public void convertAllDocumentAction(ActionEvent actionEvent) {
        viewModel.convertAllToPdf();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseFileButton.setOnAction(this::selectDocumentAction);
        convertSelectedDocumentButton.setOnAction(this::convertDocumentAction);
        convertAllDocumentButton.setOnAction(this::convertAllDocumentAction);
        setupDocumentFileTableDragDrop();
        setupDocumentFileTable();
    }
}
