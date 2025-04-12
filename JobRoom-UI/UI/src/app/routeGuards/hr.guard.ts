 import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateChild, RouterStateSnapshot, UrlTree } from '@angular/router';
import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HrGuard implements CanActivateChild  {
  constructor(private cookie:NgxEncryptCookieService){}

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

    let role = this.cookie.get("role",true,"hrd")
    // if(role !== "company")
    //  window.location.href = 'https://account.kshrd-ite.com/'
    return true;
  }
}
