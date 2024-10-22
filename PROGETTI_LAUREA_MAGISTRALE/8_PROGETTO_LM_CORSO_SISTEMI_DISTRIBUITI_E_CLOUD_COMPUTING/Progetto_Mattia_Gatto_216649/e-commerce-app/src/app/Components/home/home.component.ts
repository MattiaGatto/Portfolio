import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Carrello } from 'src/app/model/carrello';
import { Product } from 'src/app/model/product';
import { ApiService } from 'src/app/service/api.service';
import { User } from 'src/app/model/user';
import { PlaceOrder } from 'src/app/model/placeorder';
import { Address } from 'src/app/model/address';
import { FormBuilder } from '@angular/forms';
import { DettaglioCarrello } from 'src/app/model/dettagliocarrello';



@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  carrello!: Carrello;
  token!:string;
  carrelloid:any;
  productcar:any;
  products: Array<Product> = []
  
  

  constructor(private apiService: ApiService,private router: Router,private formBuilder: FormBuilder) {}

  ngOnInit() {
    if(this.apiService.isUserLoggedIn()){
      this.token=sessionStorage.getItem('email') as string;
      this.getProduct()
    }
  }

  public getProduct(): void {
    this.apiService.getProduct().subscribe(
      (response: Product[]) => {
        this.products = response;
        console.log(this.products);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
  

  

  public searchProducts(key: string): void {
    console.log(key);
    const results: Product[] = [];
    for (const product of this.products) {
      if (product.name.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(product);        
      }
    }
    this.products = results;
    if (results.length === 0 || !key) {
      this.getProduct();
    }
  }
  
  public addToCart(product:Product) : void {
    if(product.quantity>0){
      this.apiService.getCartEmail(this.token).subscribe(
        (car)=>{
        //let prod=product;
        
        this.apiService.getDetailbyCart(car).subscribe((lista_prodotti)=>{
          let bool=true;
          for(let i=0;i<lista_prodotti.length;i++){
            if(lista_prodotti[i].product.id==product.id){
              bool=false;
              lista_prodotti[i].quantity=lista_prodotti[i].quantity+1;
              this.apiService.updateDetail(lista_prodotti[i]).subscribe(
                (upda)=>{
                  console.log(upda)
                }
              );
            }
          }
          if(bool){
            const DettaglioCarrello: DettaglioCarrello = { id:0,carrello:car,product: product,quantity:1 }
            this.apiService.registerDetail(DettaglioCarrello).subscribe(
              (reg)=>{
                console.log("reggistrato"+reg);
            })
          }
        })
      })
    }
    else{
      alert("prodotto terminato!");
   }
  }

  public logout() : void {
    this.apiService.logOut();
    this.router.navigate(['/login']);
  }
}