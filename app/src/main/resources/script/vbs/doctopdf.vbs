Option Explicit
Dim str_doc_File_Path, str_pdf_File_Path

str_doc_File_Path = "DOC_FILE_PATH"
str_pdf_File_Path = "PDF_FILE_PATH"

Call Convert(str_doc_File_Path, str_pdf_File_Path)

Sub Convert(doc_Path, pdf_Path)
    Dim ObjWord
    Set ObjWord = CreateObject("Word.Application")

    If CDbl(ObjWord.Version) < 12 Then
        Wscript.Echo "Word version must be 2007 or later!"
        ObjWord.Quit
        wscript.Quit
    End If

    ObjWord.Documents.Open(doc_Path)

    Dim ObjDocument
    Set ObjDocument = ObjWord.ActiveDocument 
    ObjDocument.SaveAs pdf_Path, 17

    ObjDocument.Close
    ObjWord.Quit
End Sub