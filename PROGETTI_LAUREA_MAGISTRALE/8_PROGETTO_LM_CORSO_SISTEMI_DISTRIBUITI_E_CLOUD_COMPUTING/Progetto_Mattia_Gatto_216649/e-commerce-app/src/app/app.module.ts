import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { StorageServiceModule } from 'ngx-webstorage-service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdiminComponent } from './Components/admin/admin.component';
import { HomeComponent } from './Components/home/home.component';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { ApiService } from './service/api.service';
import { UserAuthGuard } from './service/UserAuthGuard.service';
import { AboutComponent } from './Components/home/about/about.component';
import { UserComponent } from './Components/home/user/user.component';
import { CartItemComponent } from './Components/home/cart-item/cart-item.component';
import { OrderComponent } from './Components/home/order/order.component';
import { OrderAdminComponent } from './Components/admin/order/orderadmin.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    AdiminComponent,
    CartItemComponent,
    AboutComponent,
    UserComponent,
    OrderComponent,
    OrderAdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    StorageServiceModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    
  ],
  providers: [ApiService,UserAuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
