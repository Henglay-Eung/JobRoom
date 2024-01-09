import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeGuard implements CanActivate  {

  constructor(private cookie:NgxEncryptCookieService){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
  
    let role = this.cookie.get("role",true,"hrd")
    if(role !== "employee")
    window.location.href = 'https://account.kshrd-ite.com/'
    return true;
  }
  
}
