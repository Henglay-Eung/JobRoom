import { I18nHrserviceService } from "./../i18n-hrservice.service";
import {Component, OnInit} from '@angular/core';
import { HrService } from "./../shared/hr.service";
import { NgxEncryptCookieService } from "ngx-encrypt-cookie";

// @ts-ignore
import placholder from "./../../../assets/img/default-placeholder-image.png";
import { SettingService } from "../shared/setting.service";
import { NgForm } from "@angular/forms";
import { NotificationsService } from "angular2-notifications";
import { ngxLoadingAnimationTypes } from "ngx-loading";

import { TranslateService } from "@ngx-translate/core";

@Component({
  selector: "app-setting",
  templateUrl: "./setting.component.html",
  styleUrls: ["./setting.component.css"],
})
export class SettingComponent implements OnInit {
  languages: string = "hello";
  datas = JSON.parse(sessionStorage.getItem("changlang"));

  empName;
  primaryEmail;
  secondaryEmail;
  website;
  street;
  commune;
  district;
  city;
  telephone;
  data;
  isProfile = false;
  currentPassword;
  passwordFromAuth;
  newPassword;
  comfirmPassword;
  url: string = "";
  selectedFile;
  previewImg;
  myLoading = false;
  idHr: any;

  constructor(private i18nService: I18nHrserviceService,translate: TranslateService,
    private setting: SettingService,
    private _service: NotificationsService,
    private cookie: NgxEncryptCookieService,
  ) {
    translate.setDefaultLang(this.datas);
    translate.use(this.datas);
    translate.setDefaultLang("ar");
    this.idHr = this.cookie.get("id",true,"hrd")
  }

  ngOnInit() {
    this.i18nService.localeEvent.subscribe((locale) => {
      this.languages = locale,
        sessionStorage.setItem('changlang', JSON.stringify(this.languages))
    });
    this.isProfile = true;
    this.getHrById(this.idHr);
    this.getPasswordFromAuth(this.idHr)
  }

  //TODO: to change file
  onFileChanged(event): void {
    this.selectedFile = event.target.files[0];
    if (!event.target.files[0] || event.target.files[0].length === 0) {
      return;
    }
    const mimeType = event.target.files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }
    const reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    // tslint:disable-next-line:variable-name
    reader.onload = (_event) => {
      this.previewImg = reader.result;
    };
  }

  //TODO: to update information company
  update() {
    this.myLoading = true;
    const hr = {
      name: this.empName,
      primaryEmail: this.primaryEmail,
      secondaryEmail: this.secondaryEmail,
      website: this.website,
      street: this.street,
      password: this.data.password,
      commune: this.commune,
      district: this.district,
      city: this.city,
      telephone: this.telephone,
      logo: this.url,
      banned: false,
      status: true,
    };

    if (this.selectedFile != null) {
      this.setting.uploadImage(this.selectedFile).subscribe((res) => {
        hr.logo = res.file1;
        this.setting.updateHr(this.idHr, hr).subscribe((data) => {
          this.getHrById(this.idHr);
          this._service.success(data.message, "", {
            timeOut: 2000,
            position: ["bottom", "right"],
            clickToClose: true,
            clickIconToClose: true,
            animate: "fromRight",
            maxLength: 100,
          });
          this.previewImg=placholder;
        });
      });
    } else {
      this.setting.updateHr(this.idHr, hr).subscribe((data) => {
        this.getHrById(this.idHr);
        this._service.success(data.message, "", {
          timeOut: 2000,
          position: ["bottom", "right"],
          clickToClose: true,
          clickIconToClose: true,
          animate: "fromRight",
          maxLength: 100,
        });
        this.previewImg=placholder;
      });
    }
  }

  //TODO: get data company by id
  getHrById(id) {
    this.myLoading = true;
    this.setting.getHrById(id).subscribe((data) => {
      this.empName = data.data.name;
      this.primaryEmail = data.data.primaryEmail;
      this.secondaryEmail = data.data.secondaryEmail;
      this.website = data.data.website;
      this.street = data.data.street;
      this.commune = data.data.commune;
      this.district = data.data.district;
      this.city = data.data.city;
      this.telephone = data.data.telephone;
      this.data = data.data;

      if(data.data.logo == undefined){
        this.url = "assets/img/default-thumbnail.jpg";
      }else if(data.data.logo == null){
        this.url = "assets/img/default-thumbnail.jpg";
      }else if (data.data.logo == ""){
        this.url = "assets/img/default-thumbnail.jpg";
      }else{
        this.url = data.data.logo;
      }
      
      this.previewImg = placholder;
      this.myLoading = false;
    });
  }

  //TODO: to get password from Auth
  getPasswordFromAuth(id){
    this.setting.getCompanyUserByIdOauth(id).subscribe(data=>{
      this.passwordFromAuth=data.data.password;
    })
  }

  isProfileTrue() {
    this.isProfile = true;
  }

  isProfileFalse() {
    this.isProfile = false;
  }

  //TODO: to submit form
  onSubmit(form: NgForm) {
    if (this.currentPassword != this.passwordFromAuth) {
      this._service.error("incorrect password", "", {
        timeOut: 2000,
        position: ["bottom", "right"],
        clickToClose: true,
        clickIconToClose: true,
        animate: "fromRight",
        maxLength: 100,
      });
    } else {
      const hr = {
        name: this.empName,
        primaryEmail: this.primaryEmail,
        password: this.newPassword,
      };
      this.setting.updatePassword(hr).subscribe((data) => {
        this.getHrById(this.idHr);
        this._service.success("Password has been updated successfully!", "", {
          timeOut: 2000,
          position: ["bottom", "right"],
          clickToClose: true,
          clickIconToClose: true,
          animate: "fromRight",
          maxLength: 100,
        });
      });
    }
  }}
