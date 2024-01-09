import { NgxPaginationModule } from 'ngx-pagination';
import {  FormsModule } from "@angular/forms";
import { ReactiveFormsModule } from '@angular/forms';
import { FooterComponent } from './footer/footer.component';
import { EachAnnouncementComponent } from './each-announcement/each-announcement.component';
import { NavbarComponent } from './navbar/navbar.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { SlideComponent } from './slide/slide.component';
import { HomepageComponent } from './homepage/homepage.component';
import { ViewDetailAnnouncementComponent } from './view-detail-announcement/view-detail-announcement.component';
import {RouterModule} from "@angular/router";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgSelectModule } from '@ng-select/ng-select';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BsDatepickerModule } from 'ngx-bootstrap';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import {MomentModule} from "ngx-moment";
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule,MatOptionModule, MatSelectModule, MatIconModule} from '@angular/material'
import {NgxSkeletonLoaderModule} from "ngx-skeleton-loader";
import { CardSkeletonComponent } from './card-skeleton/card-skeleton.component';
import { SimpleNotificationsModule } from 'angular2-notifications';
import {ngxLoadingAnimationTypes, NgxLoadingModule} from "ngx-loading";

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    NavbarComponent,
    EachAnnouncementComponent,
    FooterComponent,
    SlideComponent,
    HomepageComponent,
    ViewDetailAnnouncementComponent,
    CardSkeletonComponent,     
    

  ],
  exports:[
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    BsDropdownModule.forRoot(),
    RouterModule,
    NgxPaginationModule,
    MatFormFieldModule,
    MatInputModule,MatOptionModule, MatSelectModule, MatIconModule,
    ModalModule,
    NgSelectModule,
    BsDatepickerModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      },
      isolate: true,
    }),
    MomentModule,
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
export class EmployeeModule { }
