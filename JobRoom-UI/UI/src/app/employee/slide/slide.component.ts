import { ShareDataService } from './../shared/share-data.service';
import { ShareData } from './../../hr/share-data';
import { DatePipe } from '@angular/common';
import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import { EmployeeService } from './../shared/employee.service';
import { EachAnnouncementService } from './../shared/each-announcement.service';
import {Component, Input, OnInit, Output} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {I18nServiceService} from '../i18n-service.service';
import {MissingTranslationHandler, MissingTranslationHandlerParams} from '@ngx-translate/core';
import {BsDatepickerConfig} from "ngx-bootstrap";

export class MissingTranslationHelper implements MissingTranslationHandler {
  handle(params: MissingTranslationHandlerParams) {
    if (params.interpolateParams) {
      return params.interpolateParams["Default"] || params.key;
    }
    return params.key;
  }
}

@Component({
  selector: 'app-slide',
  templateUrl: './slide.component.html',
  styleUrls: ['./slide.component.css']
})
export class SlideComponent implements OnInit {
  bsValue = new Date();
  bsRangeValue: Date[];
  maxDate = new Date();
  language: string = "hello";
  startDate=new Date();
  endDate=new Date();
  searchValue;
  data;
  isLoading=false;
  itemPerPage;
  totalItem;
  totalPage=0;
  currentPage=1;
  isSearch=false;
  isFilterByDate=false;
  pipe = new DatePipe('en-US');
  bsConfig: Partial<BsDatepickerConfig> = {
    dateInputFormat: 'YYYY-MM-DD',
    showWeekNumbers: false,
  };
  datas = JSON.parse(sessionStorage.getItem("changlang"));
  employeeDetails;
  
  constructor(translate: TranslateService,
              private i18nService: I18nServiceService,
              private eachAnnouncementService: EachAnnouncementService,
              private employeeService:EmployeeService,
              private cookie:NgxEncryptCookieService,
              private shareData:ShareDataService
              ) {
    translate.setDefaultLang('en');
    translate.setDefaultLang(this.datas);
    translate.use(this.datas);
  }

  ngOnInit() {
    this.i18nService.localeEvent.subscribe((locale) => {
      this.language = locale,
        sessionStorage.setItem('changlang', JSON.stringify(this.language))
    });
    this.eachAnnouncementService.search.subscribe(data=>{
      this.getAllAnnouncement("","",this.searchValue,data-1)
    })
    this.eachAnnouncementService.filerByDate.subscribe(data=>{
      var end = this.pipe.transform(this.endDate, 'yyyy-MM-dd');
      var start = this.pipe.transform(this.startDate, 'yyyy-MM-dd');
      this.getAllAnnouncement(start,end,"",data-1)
    })
    this.getEmployeeDetails()
  }

  //TODO: get data to detail job seeker
  getEmployeeDetails(){
    let userIdAsString = this.cookie.get("userId",true,"hrd")
    // let userId = parseInt(userIdAsString)
    let userId = 3; // For testing purpose
    this.employeeService.getEmployeeDetails(userId).subscribe((res:any)=>{
      this.employeeDetails = res.data.data
    })
  }

  //TODO: to search announcement by position
  search() {
    this.isSearch=true;
    this.isFilterByDate=false;
    this.shareData.isSearchSource.next(true)
    this.shareData.isFilterByDateSource.next(false)
    this.eachAnnouncementService.currentPageSource.next(1)
    this.getAllAnnouncement("","",this.searchValue,0)
  }

  //TODO: to filter date
  filterByDate(){
    this.isSearch=false;
    this.isFilterByDate=true
    //Share data to each announcement
    this.shareData.isSearchSource.next(false)
    this.shareData.isFilterByDateSource.next(true)

    this.eachAnnouncementService.currentPageSource.next(1)
    var end = this.pipe.transform(this.endDate, 'yyyy-MM-dd');
    var start = this.pipe.transform(this.startDate, 'yyyy-MM-dd');
    this.getAllAnnouncement(start,end,"",0)
  }

  //TODO: to get all announcement
  getAllAnnouncement(startDate, endDate, position, currentPage){
    this.shareData.isLoadingSource.next(true)
    // this.isLoading=true;
    this.eachAnnouncementService.getAllAnnouncement(startDate,endDate,position,currentPage).subscribe(data=>{
      this.data=data.data;
      this.itemPerPage = data.pagination.pageSize;
      this.totalItem = data.pagination.totalItems;
      // this.isLoading=false;
      this.totalPage=data.pagination.totalPages;

      // share date to each announcement
      this.shareData.isLoadingSource.next(false)
      this.shareData.dataSource.next(data.data)
      this.shareData.itemPerPageSoure.next(data.pagination.pageSize)
      this.shareData.totalItemSource.next(data.pagination.totalItems)
      this.shareData.totalPageSource.next(data.pagination.totalPages)
    })
  }
}


