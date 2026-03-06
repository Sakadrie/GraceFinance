import * as XLSX from 'xlsx';
import { TableElement } from './TableElement';
import { FileInfo, IFileInfo } from './file-info.model';

const getFileName = (name: string): IFileInfo => {
  const timeSpan = new Date().toISOString();
  const sheetName = name || 'ExportResult';
  const fileInfo: IFileInfo = new FileInfo();
  fileInfo.fileName = `${sheetName}-${timeSpan}`;
  // eslint-disable-next-line @typescript-eslint/no-unnecessary-template-expression
  fileInfo.sheetName = `${sheetName}`;
  return fileInfo;
};

export class TableExportUtil {
  static exportToExcel(arr: Partial<TableElement>[], name: string): void {
    const { sheetName, fileName } = getFileName(name);

    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.json_to_sheet(arr);
    XLSX.utils.book_append_sheet(wb, ws, sheetName);
    XLSX.writeFile(wb, `${fileName}.xlsx`);
  }

  /* static exportToPDF(exportData: any[]): void {
    const doc = new jspdf();
    const dataValue: any = Object.keys(exportData).map(function (
      personNamedIndex: any
    ) {
      return Object.values(exportData[personNamedIndex]);
    });
    const keys: any = Object.keys(exportData[0]);

    autoTable(doc, {
      head: [keys],
      body: dataValue,
    });

    const { fileName } = getFileName('pdf');

    doc.save(`${fileName}.pdf`);
  } */
}
