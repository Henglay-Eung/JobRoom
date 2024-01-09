import { EmployeeService } from './../shared/employee.service';
import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {I18nServiceService} from '../i18n-service.service';
import {EachAnnouncementService} from "../shared/each-announcement.service";
import {DatePipe} from "@angular/common";
import {BsDatepickerConfig} from "ngx-bootstrap";
import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
  providers: [NgxEncryptCookieService]
})
export class HomepageComponent implements OnInit {
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
              private cookie:NgxEncryptCookieService) {
    translate.setDefaultLang('ar');
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

  //TODO: get data to detail employee
  getEmployeeDetails(){
    let userIdAsString = this.cookie.get("userId",true,"hrd")
    let userId = parseInt(userIdAsString)
    this.employeeService.getEmployeeDetails(userId).subscribe((res:any)=>{
      this.employeeDetails = res.data.data
    })
  }

  //TODO: search announcement by position
  search() {
    this.isSearch=true;
    this.isFilterByDate=false;
    this.eachAnnouncementService.currentPageSource.next(1)
    this.getAllAnnouncement("","",this.searchValue,0)
  }

  //TODO: filter date
  filterByDate(){
    this.isSearch=false;
    this.isFilterByDate=true
    this.eachAnnouncementService.currentPageSource.next(1)
    var end = this.pipe.transform(this.endDate, 'yyyy-MM-dd');
    var start = this.pipe.transform(this.startDate, 'yyyy-MM-dd');
    this.getAllAnnouncement(start,end,"",0)
  }

  //TODO: get all announcement
  getAllAnnouncement(startDate, endDate, position, currentPage){
    this.isLoading=true;
    this.eachAnnouncementService.getAllAnnouncement(startDate,endDate,position,currentPage).subscribe(data=>{
      this.data=data.data;
      this.itemPerPage = data.pagination.pageSize;
      this.totalItem = data.pagination.totalItems;
      this.isLoading=false;
      this.totalPage=data.pagination.totalPages;
    })
  }
}
