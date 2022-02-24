package actiangent.to.pdf.model;

import com.google.common.io.Files;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

import java.io.File;
import java.nio.file.Path;

public class DocumentFile {

    private final File document;
    private final ReadOnlyBooleanWrapper isConverted = new ReadOnlyBooleanWrapper();

    public DocumentFile(File document) {
        this.document = document;
        this.isConverted.set(false);
    }

    public Path getDocumentPath() {
        return document.toPath();
    }

    public String getDocumentPathString() {
        return document.toPath().toString();
    }

    public String getFileName() {
        return document.getName();
    }

    public String getFileNameWithoutExtension() {
        return document.getName().replaceFirst("[.][^.]+$", "");
    }

    public String getFileExtension() {
        return Files.getFileExtension(getDocumentPath().toString());
    }

    public String getFileSize() {
        return document.length() + " Bytes";
    }

    public ReadOnlyBooleanProperty isConvertedProperty() {
        return isConverted.getReadOnlyProperty();
    }

    public boolean getIsConverted() {
        return isConverted.get();
    }

    public void setIsConverted(boolean isConverted) {
        this.isConverted.set(isConverted);
    }


}
