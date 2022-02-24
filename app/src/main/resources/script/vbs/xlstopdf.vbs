Option Explicit
Dim Worksheet, str_xls_File_Path, str_pdf_File_Path

Worksheet = 1
str_xls_File_Path = "XLS_FILE_PATH"
str_pdf_File_Path = "PDF_FILE_PATH"

Call Convert(Worksheet, str_xls_File_Path, str_pdf_File_Path)

Sub Convert(sheet, xls_Path, pdf_Path)
    Dim ObjExcel 
    Set ObjExcel = CreateObject("Excel.Application")

    If CDbl(objExcel.Version) < 12 Then
        Wscript.Echo "Excel version must be 2007 or later!"
        ObjExcel.Application.Quit
        wscript.Quit
    End If

    ObjExcel.WorkBooks.Open(xls_Path)

    Dim ObjWorksheet 
    Set ObjWorksheet = ObjExcel.ActiveWorkbook.Worksheets(sheet)
    ObjWorksheet.ExportAsFixedFormat 0, pdf_Path,0, 1, 0, , , 0
    ObjExcel.ActiveWorkbook.Close
    ObjExcel.Application.Quit
End Sub