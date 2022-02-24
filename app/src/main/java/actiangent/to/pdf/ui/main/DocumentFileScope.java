package actiangent.to.pdf.ui.main;

import actiangent.to.pdf.model.DocumentFile;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DocumentFileScope {

    private final ObjectProperty<DocumentFile> selectedDocumentFile = new SimpleObjectProperty<>(this, "selectedDocumentFile");

    public ObjectProperty<DocumentFile> selectedDocumentFileProperty() {
        return selectedDocumentFile;
    }

    public DocumentFile getSelectedDocumentFile() {
        return selectedDocumentFile.get();
    }

    public void setSelectedDocumentFile(DocumentFile selectedDocumentFile) {
        this.selectedDocumentFile.set(selectedDocumentFile);
    }

}
