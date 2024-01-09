import { ngxLoadingAnimationTypes, NgxLoadingModule } from 'ngx-loading';
import { MatIconModule } from '@angular/material/icon';
import { CandidateComponent } from './candidate/candidate.component';
import { ScheduleComponent } from './schedule/schedule.component';
import { SideBarComponent } from './side-bar/side-bar.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SaveDraftListComponent } from './job-announcement/save-draft-list/save-draft-list.component';
import { CloseListComponent } from './job-announcement/close-list/close-list.component';
import { ActiveListComponent } from './job-announcement/active-list/active-list.component';
import { CreateAnnouncementComponent } from './job-announcement/create-announcement/create-announcement.component';
import { RouterModule} from "@angular/router";
import { DashboardComponent } from './dashboard/dashboard.component';
import { SettingComponent } from './setting/setting.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ViewAnnouncementComponent } from './job-announcement/view-announcement/view-announcement.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { HttpClientModule } from '@angular/common/http'
import { NgxTagsModule} from 'ngx-tags';
import { MatButtonModule, MatDatepickerModule,MatOptionModule,MatSelectModule, MatFormFieldModule, MatInputModule, MatNativeDateModule } from '@angular/material';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgSelectModule } from '@ng-select/ng-select';
import {NgxSkeletonLoaderModule} from "ngx-skeleton-loader";
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { SimpleNotificationsModule } from 'angular2-notifications';
import {MustMatchDirective} from "../validators/must-match.directive";
import { TimepickerModule } from 'ngx-bootstrap';
import { NgxMaterialTimepickerModule} from 'ngx-material-timepicker';


export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n', '.json');
}

@NgModule({
  declarations: [
    SideBarComponent,
    ScheduleComponent,
    CandidateComponent,
    SaveDraftListComponent,
    CloseListComponent,
    ActiveListComponent,
    CreateAnnouncementComponent,
    DashboardComponent,
    SettingComponent,
    ViewAnnouncementComponent,
    MustMatchDirective
  ],
  exports: [
    SideBarComponent,
    ScheduleComponent,
    CandidateComponent,
    SaveDraftListComponent,
    CloseListComponent,
    ActiveListComponent,
    DashboardComponent,
    SettingComponent,
  ],
  imports: [
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      },
      isolate: true,
    }),
    NgxMaterialTimepickerModule,
    TimepickerModule.forRoot(),
    HttpClientModule,
    CommonModule,
    RouterModule,
    NgxPaginationModule,
    ModalModule.forRoot(),
    FormsModule,
    AngularEditorModule,
    HttpClientModule,
    MatFormFieldModule,
    MatInputModule,MatOptionModule, MatSelectModule, MatIconModule,
    NgxTagsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    BsDatepickerModule.forRoot(),
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),

    NgSelectModule,
    NgxSkeletonLoaderModule.forRoot(),
    SimpleNotificationsModule.forRoot(),
    NgxLoadingModule.forRoot({animationType: ngxLoadingAnimationTypes.wanderingCubes,
      backdropBackgroundColour: 'rgba(0,0,0,0.1)', 
      backdropBorderRadius: '4px',
      primaryColour: '#00abe1', 
      secondaryColour: '#161F6D', 
      tertiaryColour: '#ffffff',
      fullScreenBackdrop: true}),
  ]
})
export class HrModule { }
