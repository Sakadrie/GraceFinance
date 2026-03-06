export interface IFileInfo {
  fileName?: string;
  sheetName?: string;
}

export class FileInfo implements IFileInfo {
  constructor(
    public fileName?: string,
    public sheetName?: string,
  ) {}
}
