import { BrowserModule } from '@angular/platform-browser';
import { NgModule, inject, provideAppInitializer } from '@angular/core';
import { HttpClient, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

//Configs
import { NeightConfiguration, NEIGHT_CONFIG } from 'src/neight.config';

//Services
import { NeightApiService } from 'src/neight-api.service';
import { neightEnvironment } from 'src/environments/environment';
import { LoginService } from './services/login.service';
import { NeightWeddingPagenotfoundComponent } from './component/neight-wedding-pagenotfound/neight-wedding-pagenotfound.component';
import { FetchGuestService } from './services/fetch-guest.service';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import {
  AccordionModule,
  BreadcrumbModule,
  ButtonModule,
  CheckboxModule,
  DatePickerModule,
  DialogModule,
  FileUploaderModule,
  GridModule,
  IconModule,
  IconService,
  InlineLoadingModule,
  InputModule,
  LoadingModule,
  ModalModule,
  NotificationModule,
  NumberModule,
  PaginationModule,
  PlaceholderModule,
  ProgressIndicatorModule,
  SearchModule,
  SkeletonModule,
  SliderModule,
  StructuredListModule,
  TableModule,
  TabsModule,
  TagModule,
  TilesModule,
  UIShellModule,
  ThemeModule
} from 'carbon-components-angular';

import {
  TranslateLoader,
  TranslateModule,
  TranslateModuleConfig,
  TranslateService,
} from '@ngx-translate/core';

import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import {
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

// @ts-ignore
import * as Icons from '@carbon/icons';
import { DashboardEffects } from './features/modules/dashboard/store/dashboard.effects';
import * as dashboardReducer from './features/modules/dashboard/store/dashboard.reducer';
import { UploadButtonFilesComponent } from './features/modules/dashboard/components/upload-button-files/upload-button-files.component';
import { DashboardOverviewComponent } from './features/modules/dashboard/pages/dashboard-overview/dashboard-overview.component';
import { RegisterGuestDialogComponent } from './features/modules/dashboard/components/register-guest-dialog/register-guest-dialog.component';
import { TranslateHttpLoader, TRANSLATE_HTTP_LOADER_CONFIG } from '@ngx-translate/http-loader';
import { catchError, of, throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from './auth/auth.service';
import { ACCESS_TOKEN_HEADER_KEY } from './models/auth.model';
import { NeightecHomeComponent } from './component/neightec-home/neightec-home.component';
import { NeightecTableComponent } from './component/neightec-table/neightec-table.component';
import { NeightecUploadListComponent } from './component/neightec-upload-list/neightec-upload-list.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader();
}

export const translateModuleConfig: TranslateModuleConfig = {
  defaultLanguage: 'de',
  isolate: false,
  loader: {
    provide: TranslateLoader,
    useFactory: HttpLoaderFactory,
    deps: [HttpClient],
  },
};

export function initializeTranslation(translate: TranslateService) {
  return () => translate.use('de').toPromise();
};

// TODO check this if necessary still
// export function initializeTranslation(translate: TranslateService) {
//   return () => {
//     if (translateModuleConfig.defaultLanguage) {
//       return translate.use(translateModuleConfig.defaultLanguage).toPromise();
//     }
//     return of(true).toPromise();
//   };
// }

@NgModule({ 
  declarations: [
    AppComponent,
    NeightWeddingPagenotfoundComponent,
    UploadButtonFilesComponent,
    DashboardOverviewComponent,
    RegisterGuestDialogComponent,

    //new components base on angular
    NeightecHomeComponent,
    NeightecTableComponent,
    NeightecUploadListComponent,
  ],
  bootstrap: [AppComponent], 
  imports: [
    MatListModule,
    MatSidenavModule,
    MatButtonModule,
    MatTabsModule,
    MatTableModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatIconModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    StoreModule.forRoot({}),
    EffectsModule.forRoot([]),
    StoreModule.forFeature(dashboardReducer.dashboardReducerKey, dashboardReducer.dashboardReducer),
    EffectsModule.forFeature([
        DashboardEffects,
    ]),
    TranslateModule.forRoot(translateModuleConfig),
    IconModule,
    UIShellModule,
    ThemeModule,
    SearchModule,
    SkeletonModule,
    TabsModule,
    TableModule,
    PaginationModule,
    ButtonModule,
    ModalModule,
    PlaceholderModule,
    InputModule
    ], 
    providers: [
      NeightApiService,
      LoginService,
      FetchGuestService,
      { provide: NEIGHT_CONFIG, useValue: neightEnvironment },
      {
        provide: TRANSLATE_HTTP_LOADER_CONFIG,
        useValue: {
          prefix: './assets/i18n/',
          suffix: '.json',
        }
      },
      provideAppInitializer(() => {
      const initializerFn = (initializeTranslation)(inject(TranslateService));
      return initializerFn();
    }),
      provideHttpClient(withInterceptorsFromDi()),
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA], })
export class AppModule { 

  groupedIcons: any[] = [];
  constructor(protected iconService: IconService) {
    const iconMap = new Map();

    for (const [_, descriptor] of Object.entries(Icons) as any) {
      this.iconService.register(descriptor as object);
      if (!iconMap.has(descriptor['name'])) {
        iconMap.set(descriptor['name'], []);
      }
      iconMap.get(descriptor['name']).push(descriptor);
    }
    this.groupedIcons = Array.from(iconMap.values());
  }

}
