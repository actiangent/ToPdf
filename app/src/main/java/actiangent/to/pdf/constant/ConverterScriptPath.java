package actiangent.to.pdf.constant;

import actiangent.to.pdf.App;

import java.net.URL;
import java.util.Objects;

public class ConverterScriptPath {

    public static final URL DOC_TO_PDF_SCRIPT_PATH_URL = Objects.requireNonNull(App.class.getResource("/script/vbs/doctopdf.vbs"));
    public static final URL XLS_TO_PDF_SCRIPT_PATH_URL = Objects.requireNonNull(App.class.getResource("/script/vbs/xlstopdf.vbs"));
    public static final URL PPT_TO_PDF_SCRIPT_PATH_URL = Objects.requireNonNull(App.class.getResource("/script/vbs/ppttopdf.vbs"));
    public static String CONVERTED_FOLDER_PATH = System.getProperty("user.home") + "/Documents/ToPDFConverted/";

}
