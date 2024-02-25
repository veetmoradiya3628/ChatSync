import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HTTP_INTERCEPTORS, HttpResponse, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, tap, throwError } from "rxjs";
import { AuthService } from "../service/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private _authService: AuthService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let authReq = req;
        const token = this._authService.getToken();
        if (token != null) {
            authReq = authReq.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`
                }
            })
        }
        return next.handle(authReq).pipe(
            tap((event: HttpEvent<any>) => {
                if(event instanceof HttpResponse && event.status === 401){
                    console.log('Unauthorized response. Logging out...');
                    this._authService.logout();
                }
            }),
            catchError((error: HttpErrorResponse) => {
                if (error.status === 401) {
                    // Handle 401 response here (e.g., log the user out)
                    console.log('Unauthorized response. Logging out...');
                    this._authService.logout();  // Assuming AuthService has a logout method
                }
                return throwError(error);
            })
        );
    }
}

export const authInterceptorProviders = [
    {
        provide: HTTP_INTERCEPTORS,
        useClass: AuthInterceptor,
        multi: true
    }
]