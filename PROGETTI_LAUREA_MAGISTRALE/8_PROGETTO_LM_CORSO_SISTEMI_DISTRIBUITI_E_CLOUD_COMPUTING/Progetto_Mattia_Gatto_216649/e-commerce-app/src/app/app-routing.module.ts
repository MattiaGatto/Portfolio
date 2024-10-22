import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './Components/home/about/about.component';
import { AdiminComponent } from './Components/admin/admin.component';
import { HomeComponent } from './Components/home/home.component';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { UserAuthGuard } from './service/UserAuthGuard.service';
import { UserComponent } from './Components/home/user/user.component';
import { CartItemComponent } from './Components/home/cart-item/cart-item.component';
import { OrderComponent } from './Components/home/order/order.component';
import { OrderAdminComponent } from './Components/admin/order/orderadmin.component';


const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent, canActivate:[UserAuthGuard] },
  { path: 'admin', component: AdiminComponent, canActivate : [UserAuthGuard] },
  { path: 'cart', component: CartItemComponent, canActivate : [UserAuthGuard] },
  { path: 'info', component: AboutComponent, canActivate : [UserAuthGuard] },
  { path: 'user', component: UserComponent, canActivate : [UserAuthGuard] },
  {path:'order',component:OrderComponent, canActivate : [UserAuthGuard]},
  {path:'orderadmin',component:OrderAdminComponent, canActivate : [UserAuthGuard]},

  // otherwise redirect to login
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
