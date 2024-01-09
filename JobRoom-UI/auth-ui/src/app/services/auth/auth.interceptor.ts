import { HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


import { AuthService } from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private authService:AuthService,private _http: HttpClient){}
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
    let token
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