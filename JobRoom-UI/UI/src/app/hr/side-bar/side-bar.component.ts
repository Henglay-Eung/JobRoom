import { SettingService } from './../shared/setting.service';
import { Router } from '@angular/router';
import { HrService } from './../shared/hr.service';
import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import { I18nHrserviceService } from "./../i18n-hrservice.service";
import { Component, Renderer2, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { MatIconRegistry } from "@angular/material";
import * as moment from "moment";

@Component({
  selector: "app-side-bar",
  templateUrl: "./side-bar.component.html",
  styleUrls: ["./side-bar.component.css"],
})
export class SideBarComponent implements OnInit {

  title = "ngx-i18n";
  languagess: string = "";
  data = JSON.parse(sessionStorage.getItem("changlang"));
  isHide: boolean = false;
  idHr: any;
  hrLogo: string;

  constructor(
    private icon: MatIconRegistry,
    private renderer: Renderer2,
    private translate: TranslateService,
    private i18nService: I18nHrserviceService,
    private cookie: NgxEncryptCookieService,
    private router: Router,
    private hrService: HrService,
    private setting: SettingService
  ) {
    this.renderer.listen("window", "click", () => {
      this.isHide = false;
    });

    this.translate.setDefaultLang(this.data);
    this.translate.use(this.data);
    this.idHr = this.cookie.get("id",true,"hrd")
  }

  //read only switch language
  readonly languages = [
    { value: "ar", label: "ខ្មែរ", img: "assets/img/khmer.png" },
    { value: "en", label: "English", img: "assets/img/english.png" },
  ];

  public language = this.languages[0];
  //function change local string
  changeLocale(locale: string) {
    this.language = this.languages.find((lang) => lang.value === locale);
    this.i18nService.changeLocale(locale);
    this.languagess = locale;
    sessionStorage.setItem("changlang", JSON.stringify(this.languagess));
  }

  //TODO: to load local switch language
  loadLocale(lang: string): Promise<string> {
    if (lang === "en") {
      return Promise.resolve("en");
    } else if (lang == "ar") {
      return Promise.resolve("ar");
    }
    return import(`moment/locale/${lang}.js`).then(() => moment.locale(lang));
  }

  //TODO: to toggle open and close menu
  toggleMenu() {
    this.isHide = !this.isHide;
  }

  ngOnInit() {
    this.setting.refreshNeed.subscribe(() => {
      this.hrService.getHrDetails(this.idHr).subscribe((res: any)=>{
        if(res.data.logo == undefined){
          this.hrLogo = "https://www.speakersbank.org.au/wp-content/uploads/2016/01/photo-icon.png";
        }else if(res.data.logo == null){
          this.hrLogo = "https://www.speakersbank.org.au/wp-content/uploads/2016/01/photo-icon.png";
        }else if(res.data.logo == ""){
          this.hrLogo = "https://www.speakersbank.org.au/wp-content/uploads/2016/01/photo-icon.png";
        }else{
          this.hrLogo = res.data.logo;
        }
      })
    });
    this.hrService.getHrDetails(this.idHr).subscribe((res: any)=>{
      if(res.data.logo == undefined){
        this.hrLogo = "https://www.speakersbank.org.au/wp-content/uploads/2016/01/photo-icon.png";
      }else if(res.data.logo == null){
        this.hrLogo = "https://www.speakersbank.org.au/wp-content/uploads/2016/01/photo-icon.png";
      }else if(res.data.logo == ""){
        this.hrLogo = "https://www.speakersbank.org.au/wp-content/uploads/2016/01/photo-icon.png";
      }else{
        this.hrLogo = res.data.logo;
      }
    })
    this.icon.registerFontClassAlias("fontawesome", "fa");
  }

  //TODO: to logout
  logout(){
    this.cookie.deleteAll("/",".kshrd-ite.com")
  }
}
