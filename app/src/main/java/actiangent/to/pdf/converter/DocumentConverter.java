package actiangent.to.pdf.converter;

import actiangent.to.pdf.model.DocumentFile;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static actiangent.to.pdf.constant.ConverterScriptPath.*;

public class DocumentConverter {

    private final Executor executor = Executors.newCachedThreadPool(runnable -> {
        Thread workerThread = new Thread(runnable);
        workerThread.setDaemon(true); // when exit, threads also die
        return workerThread;
    });

    public void convertToPDF(DocumentFile documentFile) {
        executor.execute(createConvertTask(documentFile));
    }

    private Task<Boolean> createConvertTask(DocumentFile documentFile) {
        return new Task<>() {
            @Override
            protected Boolean call() throws IOException, URISyntaxException, InterruptedException {
                Path converterScript = makeTempConverterScriptFile(documentFile.getFileExtension().substring(0, 3), documentFile);
                converterScript.toFile().deleteOnExit();

                Process taskProcess = new ProcessBuilder("wscript", converterScript.toString()).start();
                String wscriptOutput = new String(taskProcess.getInputStream().readAllBytes());
                if (!wscriptOutput.isEmpty()) {
                    System.out.println(wscriptOutput);
                    return false;
                }

                return taskProcess.waitFor() == 0;
            }

            @Override
            protected void succeeded() {
                documentFile.setIsConverted(true);
            }


        };
    }


    private Path makeTempConverterScriptFile(String type, DocumentFile documentFile) throws IOException, URISyntaxException {
        Path tempConverterScript = Files.createTempFile(type + "-temp-script", ".vbs");

        switch (type) {
            case "doc":
                List<String> docConverterScript = Files.readAllLines(Paths.get(DOC_TO_PDF_SCRIPT_PATH_URL.toURI()));
                for (int i = 0; i < docConverterScript.size(); i++) {
                    docConverterScript.set(i, docConverterScript.get(i).replace("DOC_FILE_PATH", documentFile.getDocumentPathString()));
                    docConverterScript.set(i, docConverterScript.get(i).replace("PDF_FILE_PATH", CONVERTED_FOLDER_PATH + documentFile.getFileNameWithoutExtension() + ".pdf"));
                }

                Files.write(tempConverterScript, docConverterScript);
                break;

            case "xls":
                List<String> xlsConverterScript = Files.readAllLines(Paths.get(XLS_TO_PDF_SCRIPT_PATH_URL.toURI()));
                for (int i = 0; i < xlsConverterScript.size(); i++) {
                    xlsConverterScript.set(i, xlsConverterScript.get(i).replace("XLS_FILE_PATH", documentFile.getDocumentPathString()));
                    xlsConverterScript.set(i, xlsConverterScript.get(i).replace("PDF_FILE_PATH", CONVERTED_FOLDER_PATH + documentFile.getFileNameWithoutExtension() + ".pdf"));
                }

                Files.write(tempConverterScript, xlsConverterScript);
                break;

            case "ppt":
                List<String> pptConverterScript = Files.readAllLines(Paths.get(PPT_TO_PDF_SCRIPT_PATH_URL.toURI()));
                for (int i = 0; i < pptConverterScript.size(); i++) {
                    pptConverterScript.set(i, pptConverterScript.get(i).replace("PPT_FILE_PATH", documentFile.getDocumentPathString()));
                    pptConverterScript.set(i, pptConverterScript.get(i).replace("PDF_FILE_PATH", CONVERTED_FOLDER_PATH + documentFile.getFileNameWithoutExtension() + ".pdf"));
                }

                Files.write(tempConverterScript, pptConverterScript);
                break;

        }

        return tempConverterScript;
    }

}
