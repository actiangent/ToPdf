Option Explicit
Dim str_ppt_File_Path, str_pdf_File_Path

str_ppt_File_Path = "PPT_FILE_PATH"
str_pdf_File_Path = "PDF_FILE_PATH"

Call Convert(str_ppt_File_Path, str_pdf_File_Path)

Sub Convert(ppt_Path, pdf_Path)
    Dim ObjPowerPoint
    Set ObjPowerPoint = CreateObject("PowerPoint.Application")

    If CDbl(ObjPowerPoint.Version) < 12 Then
        Wscript.Echo "PowerPoint version must be 2007 or later!"
        ObjPowerPoint.Quit
        wscript.Quit
    End If

    Dim ObjPresentation
    Set ObjPresentation = ObjPowerPoint.Presentations.Open(ppt_Path, True, False, False)
    ObjPresentation.SaveAs pdf_Path, 32

    ObjPresentation.Close
    ObjPowerPoint.Quit
End Sub