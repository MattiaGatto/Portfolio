import { Injectable } from '@angular/core';      
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';      
import { ApiService } from './api.service';
@Injectable({      
   providedIn: 'root'      
})      
export class UserAuthGuard implements CanActivate {      
  constructor(private auth: ApiService,private router: Router) { }      
  
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {      
    if (this.auth.isUserLoggedIn()){
      return true;
    }else{ 
      this.router.navigate(['/login']);
      return false;
    }   
  } 
}   
