import { createReducer, on } from "@ngrx/store";
import { addDashboardFiles, loadDataDashboardStateError, loadDataDashboardStateSuccess, loadDataGuestAfterDeleteStateError, loadDataGuestAfterDeleteStateSuccess, uploadDashboardFiles } from "./dashboard.actions";
import { GuestDTO } from "src/app/dto/GuestDTO";
import { FileItem } from "src/app/models/file-item.model";
import { unionBy } from 'lodash';

export const dashboardReducerKey = 'dashboard-reducer-key';

export interface DashboardState {
  data: GuestDTO[] | [];
  files: FileItem[] | [];
}

export const initialState: DashboardState = {
  data: [],
  files: [],
};

export const dashboardReducer = createReducer(
  initialState,
  on(
    loadDataDashboardStateSuccess,
    (state, action) => ({
      ...state as DashboardState,
      data: action.data,
    })
  ),
  on(
    loadDataDashboardStateError,
    (state, action) => ({
      ...state as DashboardState,
      data: null,
    })
  ),
  on(
    loadDataGuestAfterDeleteStateSuccess,
    (state, action) => ({
      ...state as DashboardState,
      data: action.data,
    })
  ),
  on(
    loadDataGuestAfterDeleteStateError,
    (state, action) => ({
      ...state as DashboardState,
      data: null,
    })
  ),
  on(
    addDashboardFiles,
    (state, { files }): DashboardState => ({
      ...state,
      files:  unionBy(files, state.files, 'name'),
    })
  ),
  on(
    uploadDashboardFiles,
    (state, { files }): DashboardState => ({
      ...state,
      files: state.files.map(fileItem =>
        fileItem === files
          ? { ...fileItem, invalid: false, state: 'upload' }
          : fileItem
      ),
    })
  )
  
);