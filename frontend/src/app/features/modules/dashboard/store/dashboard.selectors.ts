import { createFeatureSelector, createSelector } from "@ngrx/store";
// import { DashboardFeatureKey, DashboardState } from "./dashboard.state";
import * as DashBoardReducer from './dashboard.reducer';

export const selectFeature = createFeatureSelector<DashBoardReducer.DashboardState>(DashBoardReducer.dashboardReducerKey);

export const selectDashboard = createSelector(selectFeature, (state: DashBoardReducer.DashboardState) => state.data); 