import { createAction, props } from "@ngrx/store";
import { GuestDTO } from "src/app/dto/GuestDTO";
import { FileItem } from "src/app/models/file-item.model";

enum DashboardActions { 
  InitDashboard = '[Dashboard] Init',
  LoadDataDashboardState = '[Core] Load Data Dashboard',
  LoadDataDashboardStateSuccess = '[Core] Load Data Dashboard Success',
  LoadDataDashboardStateError = '[Core] Load Data Dashboard Error',
  LoadDataGuestAfterDeleteStateSuccess = '[Core] Load Data Guest Dashboard After Delete success',
  LoadDataGuestAfterDeleteStateFailure = '[Core] Load Data Guest Dashboard After Delete failure',
  AddDashboardFiles = '[Core] Add Files in Dashboard',
  UploadDashboardFiles = '[Core] Upload Files in Dashboard',
  UploadDashboardFilesSuccess = '[Edit Campaign] Upload Campaign File Success',
  UploadDashboardFilesError = '[Edit Campaign] Upload Campaign File Error',
}

export const initDashboard = createAction(DashboardActions.InitDashboard, props<{ data: GuestDTO[] }>());

export const loadDataDashboardState = createAction(DashboardActions.LoadDataDashboardState);
export const loadDataDashboardStateSuccess = createAction(
  DashboardActions.LoadDataDashboardStateSuccess,
  props<{data: GuestDTO[]}>()
);
export const loadDataDashboardStateError = createAction(DashboardActions.LoadDataDashboardStateError);

export const loadDataGuestAfterDeleteStateSuccess = createAction(
  DashboardActions.LoadDataGuestAfterDeleteStateSuccess,
  props<{data: GuestDTO[]}>()
);
export const loadDataGuestAfterDeleteStateError = createAction(DashboardActions.LoadDataGuestAfterDeleteStateFailure);

export const updateDataDashboard = createAction(
  '[Dashboard Actions] add guest to dashboard',
  props<{guests: string[]}>()
);

export const deleteGuestsFrom = createAction(
  '[Dashboard Actions] delete guest to dashboard',
  props<{guests: string[]}>()
);

export const addDashboardFiles = createAction(
  DashboardActions.AddDashboardFiles,
  props<{ files: FileItem[] }>()
);

export const uploadDashboardFiles = createAction(
  DashboardActions.AddDashboardFiles,
  props<{ files: FileItem[] }>()
);
export const uploadCampaignFileSuccess = createAction(
  DashboardActions.UploadDashboardFilesSuccess,
  props<{ file: FileItem }>()
);
export const uploadCampaignFileError = createAction(
  DashboardActions.UploadDashboardFilesError,
  props<{ file: FileItem }>()
);