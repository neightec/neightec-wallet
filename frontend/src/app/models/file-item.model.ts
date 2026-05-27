export interface FileItem {
  id: string | null;
  name: string;
  state: 'edit' | 'upload' | 'complete';
  uploaded: boolean;
  invalid?: boolean;
  invalidSize?: boolean;
  file?: File;
}
