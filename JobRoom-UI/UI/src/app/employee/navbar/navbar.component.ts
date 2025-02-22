import { Component, OnInit, Renderer2 } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { I18nServiceService } from '../i18n-service.service';
import { FormBuilder,FormsModule, FormGroup }   from '@angular/forms';
import { MatIconRegistry } from '@angular/material';

//import { Moment } from 'moment';
import * as moment from 'moment';
import {NgxEncryptCookieService} from "ngx-encrypt-cookie";
import {EmployeeService} from "../shared/employee.service";
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
  title = 'ngx-i18n';
  languagess:string="";
  countryForm: FormGroup;
  countries = ['USA', 'Canada', 'Uk'];
  userID: string;
  employee: object;
  data= JSON.parse(sessionStorage.getItem("changlang"));
  isHideDialog: boolean = false;
  constructor(
      private icon: MatIconRegistry,
      private renderer: Renderer2,
      private translate: TranslateService,
      private i18nService: I18nServiceService,
      private fb: FormBuilder,private cookie: NgxEncryptCookieService,
      private employeeService: EmployeeService
  ) {
      this.translate.setDefaultLang(this.data);
      this.translate.use(this.data);
      translate.setDefaultLang('ar');
      this.userID = this.cookie.get("id",true,"hrd");
  }

  toggleDialog() {
    this.isHideDialog = !this.isHideDialog;
  }

  //read only switch language
  readonly languages = [
    { value: 'ar', label: 'ខ្មែរ', img: 'assets/img/khmer.png'},
    { value: 'en', label: 'English', img: 'assets/img/english.png' },
  ];

  public language = this.languages[0];

  //function change local string
  changeLocale(locale: string) {
    this.language = this.languages.find( lang => lang.value === locale);
    this.i18nService.changeLocale(locale);
    this.languagess=locale;
    sessionStorage.setItem('changlang',JSON.stringify(this.languagess));
  }

  loadLocale(lang: string): Promise<string> {
    if(lang === 'en') {
      return Promise.resolve('en');
      }
    else if(lang=='ar'){
      return Promise.resolve('ar');
    }
  }

  ngOnInit(){
    this.employeeService.getEmployeeDetails(this.userID).subscribe((res: any)=>{
      this.employee = res.data;
    })
    this.icon.registerFontClassAlias('fontawesome', 'fa');

    this.employee = {
      fullName: "fullName",
      email: "henglayeung@gmail.com",
      profilePicture: "https://media.licdn.com/dms/image/v2/D4E35AQEqiPQL8GH7VA/profile-framedphoto-shrink_400_400/profile-framedphoto-shrink_400_400/0/1728525788195?e=1740798000&v=beta&t=fKZu7jBFUsDH6YcsDJcC67V96TzLe88ATiYLBsyQnxA",
      uuid: "1231232131",

    }
  }
    //TODO: click to logout
    logout(){
      this.cookie.deleteAll("/",".kshrd-ite.com")
    }
}
