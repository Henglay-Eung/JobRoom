import { HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgxEncryptCookieService } from 'ngx-encrypt-cookie';
import { Observable } from 'rxjs';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private cookie:NgxEncryptCookieService){}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  

    let user = {
        client_id: 'fIEZTLtFy3YutyKDcFadMeZ3cb5BouLP',
        client_secret: '9bnm8sV0HwgcMx6pqk9x0WWR9tCzYyGi',
        username: "jobroom",
        password: "jobroom",
        grant_type: "password",
        scope: "email",
        provision_key: 'AU3p0FF7yU4mA8thdUGG8xdpOV45Iwjb',
        authenticated_userid: "connex"
      }
    let token =  this.cookie.get("token",true,"hrd")
    if (req.url.search("token") === -1 ) {
        req = req.clone({
          setHeaders: {
            'Authorization': `Bearer ${token}`,
          }
        });
       
    }
    return next.handle(req);
  }
}