package actiangent.to.pdf.repository;

import actiangent.to.pdf.converter.DocumentConverter;
import actiangent.to.pdf.model.DocumentFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;

import static actiangent.to.pdf.constant.ConverterScriptPath.CONVERTED_FOLDER_PATH;

public class DocumentsRepository {

    static {
        File convertedFolderDirectory = new File(CONVERTED_FOLDER_PATH);
        if (!convertedFolderDirectory.exists()) {
            convertedFolderDirectory.mkdir();
        }
    }

    private final ObservableList<DocumentFile> documentFilesList = FXCollections.observableArrayList();
    private final FileChooser.ExtensionFilter extensionFilter = new FileChooser
            .ExtensionFilter("Microsoft Documents", "*.pptx", "*.ppt", "*.xlsx", "*.xls", "*.docx", "*.doc");
    private final DocumentConverter documentConverter;

    public DocumentsRepository(DocumentConverter documentConverter) {
        this.documentConverter = documentConverter;
    }

    public void addDocumentFilesToList(DocumentFile document) {
        documentFilesList.add(document);
    }

    public ObservableList<DocumentFile> getDocumentFilesList() {
        return documentFilesList;
    }

    public FileChooser.ExtensionFilter getExtensionFilter() {
        return extensionFilter;
    }

    public void convertToPdf(DocumentFile documentFile) {
        documentConverter.convertToPDF(documentFile);
    }

    public void convertAllToPdf() {
        documentFilesList.forEach(documentConverter::convertToPDF);
    }

}
