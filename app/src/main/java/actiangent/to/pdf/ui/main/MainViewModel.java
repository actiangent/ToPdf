package actiangent.to.pdf.ui.main;

import actiangent.to.pdf.model.DocumentFile;
import actiangent.to.pdf.repository.DocumentsRepository;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

public class MainViewModel {

    private final DocumentsRepository documentsRepository;
    private final DocumentFileScope documentFileScope;

    public MainViewModel(DocumentsRepository documentsRepository, DocumentFileScope documentFileScope) {
        this.documentsRepository = documentsRepository;
        this.documentFileScope = documentFileScope;
    }

    public ObservableList<DocumentFile> getDocumentFilesList() {
        return documentsRepository.getDocumentFilesList();
    }

    public void addDocumentFilesToList(DocumentFile document) {
        documentsRepository.addDocumentFilesToList(document);
    }

    public FileChooser.ExtensionFilter getExtensionFilter() {
        return documentsRepository.getExtensionFilter();
    }

    public ObjectProperty<DocumentFile> selectedDocumentFileProperty() {
        return documentFileScope.selectedDocumentFileProperty();
    }

    public void convertToPdf() {
        if (documentFileScope != null) {
            int indexOfSelectedDocument = getDocumentFilesList().indexOf(documentFileScope.getSelectedDocumentFile());
            documentsRepository.convertToPdf(getDocumentFilesList().get(indexOfSelectedDocument));
        }
    }

    public void convertAllToPdf() {
        documentsRepository.convertAllToPdf();
    }


}
