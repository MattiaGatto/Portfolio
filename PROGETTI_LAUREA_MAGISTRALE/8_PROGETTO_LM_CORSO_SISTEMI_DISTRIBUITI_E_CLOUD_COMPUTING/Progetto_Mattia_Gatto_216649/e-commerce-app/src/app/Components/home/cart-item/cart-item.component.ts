import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/service/api.service';
import { Product } from 'src/app/model/product';
import { HttpErrorResponse, HttpHeaderResponse } from '@angular/common/http';
import { PlaceOrder } from 'src/app/model/placeorder';
import { DettaglioCarrello } from 'src/app/model/dettagliocarrello';
import { FormBuilder, NgForm } from '@angular/forms';
import { CreditCard } from 'src/app/model/creditcard';
import { DettaglioOrdine } from 'src/app/model/dettaglioordine';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.css']
})
export class CartItemComponent implements OnInit {

  auth!:string;
  totalSum: number = 0;
  dettagliocarrello: Array<DettaglioCarrello> = []
  
  orderinfo: String[]=[];
  orderForm:any;
  creditForm:any;

  carinfo:String[]=[];

  constructor(private api: ApiService, private route: Router, private formBuilder: FormBuilder) {
    this.getinfo();
    this.createFormAddress();
    this.createCard();
  }
  

  ngOnInit() {
    this.getCarProd();  
  }

  getCarProd() {
    this.auth =sessionStorage.getItem('email') as string;
    this.api.userGetEmail(this.auth).subscribe(
      (user) => {
        this.api.getCartEmail(this.auth).subscribe(
          (car) => {
            this.api.getDetailbyCart(car).subscribe(
              (det) =>{
                for (let i=0; i<det.length;i++){
                  this.dettagliocarrello[i]=det[i];
                  this.totalSum = this.totalSum + (det[i].quantity * det[i].product.price);
                 
                }
                console.log(this.dettagliocarrello);

              }
            );
          })
    });
  }


  updatedet(dettagliocarrello:DettaglioCarrello){
    var quantity = parseFloat((<HTMLInputElement>document.getElementById("newquantity_"+dettagliocarrello.product.id)).value);
    this.totalSum = this.totalSum - (dettagliocarrello.quantity * dettagliocarrello.product.price)
    dettagliocarrello.quantity=quantity;
    //this.products[i]=listadetail[i].product;
    this.totalSum = this.totalSum + (dettagliocarrello.quantity * dettagliocarrello.product.price);
    this.api.updateDetail(dettagliocarrello).subscribe(
      (p)=>{
        console.log("aggiornato"+p);
        this.getCarProd()
      }
    );
  }

  delete(dettaglio:DettaglioCarrello) {
    this.totalSum = this.totalSum - (dettaglio.quantity * dettaglio.product.price)
    this.api.deleteDetail(dettaglio.id).subscribe(
        (response: void) => {
          console.log(response);
         
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
  }


  getinfo(){
    this.auth = sessionStorage.getItem('email') as string;
    this.api.userGetEmail(this.auth).subscribe((user)=>{
      this.api.getAddressId(user.address.id).subscribe((address)=>{
        this.orderinfo[0]=address.address;
        this.orderinfo[1]=address.city;
        this.orderinfo[2]=address.state ;
        this.orderinfo[3]=address.country;
        this.orderinfo[4]=address.zipcode as unknown as String;
        this.orderinfo[5]=address.phonenumber;
        this.orderinfo[6]=address.email;
        this.orderinfo[7]=address.nome;
        this.orderinfo[8]=address.cognome;
        this.api.getCreditCardbyEmail(this.auth).subscribe((cart)=>{
          this.carinfo[0]=cart.nomecard;
          this.carinfo[1]=cart.cognomecard;
          this.carinfo[2]=cart.creditnumber;
          this.carinfo[3]=cart.creditcode;
          this.carinfo[4]=cart.expiration_date;
          this.carinfo[5]=cart.credit as unknown as string;
        });
      });
    });
  }

  createFormAddress() {
    this.orderForm = this.formBuilder.group({
      orderStatus:"Completato",
      orderDate:new Date,
      address: this.orderinfo[0],
      city:  this.orderinfo[1],
      state:  this.orderinfo[2],
      country:  this.orderinfo[3],
      zipcode:  this.orderinfo[4],
      phone:  this.orderinfo[5],
      email:this.orderinfo[6],
      nome:this.orderinfo[7],
      cognome:this.orderinfo[8],
      description:"",
      totalCost:this.totalSum,
      creditCard:null,
      useremail:this.auth,

      nomecard: this.carinfo[0],
      cognomecard: this.carinfo[1],
      numbercard: this.carinfo[2],
      codecard:this.carinfo[3],
      datacard:this.carinfo[4],
    });
  }


  controllaAddressOrder(addressForm: NgForm){
    if(addressForm.value.address==""){
      addressForm.value.address=this.orderinfo[0] as string;
    }
    if(addressForm.value.city==""){
      addressForm.value.city=this.orderinfo[1] as string;
    }
    if(addressForm.value.state==""){
      addressForm.value.state=this.orderinfo[2] as string;
    }
    if(addressForm.value.country==""){
      addressForm.value.country=this.orderinfo[3] as string;
    }
    if(addressForm.value.zipcode==""){
      addressForm.value.zipcode=this.orderinfo[4] as string;
    }
    if(addressForm.value.phone==""){
      addressForm.value.phone=this.orderinfo[5] as string;
    }
    if(addressForm.value.email==""){
      addressForm.value.email=this.orderinfo[6] as string;
    }
    if(addressForm.value.nome==""){
      addressForm.value.nome=this.orderinfo[7] as string;
    }
    if(addressForm.value.cognome==""){
      addressForm.value.cognome=this.orderinfo[8] as string;
    }
    if(addressForm.value.nomecard==""){
      addressForm.value.nomecard=this.carinfo[0] as string;
    }
    if(addressForm.value.cognomecard==""){
      addressForm.value.cognomecard=this.carinfo[1] as string;
    }
    if(addressForm.value.numbercard==""){
      addressForm.value.numbercard=this.carinfo[2] as string;
    }
    if(addressForm.value.codecard==""){
      addressForm.value.codecard=this.carinfo[3] as string;
    }
    if(addressForm.value.datacard==""){
      addressForm.value.datacard=this.carinfo[4] as string;
    }
        
  }


  public createCard(){
    this.creditForm = this.formBuilder.group({
      id:0,
      emailcard:"",
      nomecard: this.carinfo[0],
      cognomecard: this.carinfo[1],
      creditnumber:this.carinfo[2],
      creditcode:this.carinfo[3],
      expiration_date:this.carinfo[4],
      credit:1000,
      });
  }

  placeorder(Form:NgForm) {
    this.auth = sessionStorage.getItem('email') as string;
    this.api.userGetEmail(this.auth).subscribe(
      (user) => {
        this.api.getCartEmail(this.auth).subscribe(
          (car) => {
            this.api.getDetailbyCart(car).subscribe(
              (lista_prod)=>{
                this.getinfo();
                this.controllaAddressOrder(Form);
                this.orderForm.value.city=Form.value.city;
                this.orderForm.value.address=Form.value.address;
                this.orderForm.value.country=Form.value.country;
                this.orderForm.value.phonenumber=Form.value.phone;
                this.orderForm.value.state=Form.value.state;
                this.orderForm.value.zipcode=Form.value.zipcode;
                this.orderForm.value.email=Form.value.email;
                this.orderForm.value.nome=Form.value.nome;
                this.orderForm.value.cognome=Form.value.cognome;
                this.orderForm.value.totalCost=this.totalSum;
                this.orderForm.value.description=Form.value.description;
                this.orderForm.value.useremail=this.auth

                this.creditForm.value.emailcard=user.email;
                this.creditForm.value.nomecard=Form.value.nomecard;
                this.creditForm.value.cognomecard=Form.value.cognomecard;
                this.creditForm.value.creditnumber=Form.value.numbercard;
                this.creditForm.value.creditcode=Form.value.codecard;
                this.creditForm.value.expiration_date=Form.value.datacard;
                this.creditForm.value.credit=1000-this.totalSum;
                const CreditCard: CreditCard = this.creditForm.value;
                if(this.verificainfo(Form,CreditCard)){
                  if(this.controlla_prodotti(lista_prod) &&lista_prod.length!=0){
                    this.api.getCreditCardbyEmail(this.auth).subscribe((cart)=>{
                      if(cart.credit-this.totalSum>=0){
                        cart.credit=cart.credit-this.totalSum
                        this.api.updateCreditCard(cart).subscribe((carta)=>{
                          this.orderForm.value.creditCard=carta;
                          this.api.registerPlaceOrder(this.orderForm.value).subscribe(
                            (res)=>{
                              console.log(res);
                              alert("Ordine completato")
                              this.clearcar(lista_prod,res);
                            })
                          })
                      }else{alert ("credito non sucfficiente")}
                    },(error:HttpHeaderResponse)=>{
                      this.api.registerCreditCard(CreditCard).subscribe((ris)=>{
                        this.orderForm.value.creditCard=ris;
                        this.api.registerPlaceOrder(this.orderForm.value).subscribe(
                          (res)=>{
                            console.log(res);
                            alert("Ordine completato")
                            this.clearcar(lista_prod,res);
                          })
                      })
                    });
                }else{
                  if(lista_prod.length==0){alert("Carrello vuoto")}
                  else{alert("Quantità prodotti non disponibili")}
                }
              }
                
          });
        });
      });
  }

  public verificainfo(orderForm:NgForm,CreditCard:CreditCard) :boolean{
    let bool:boolean=true;
    if(orderForm.value.address==""){
      alert("indirizzo non segnalato")
      bool=false;
    }
    if(bool&&orderForm.value.city==""){
      alert("città non definita")
      bool=false;
    }
    if(bool&&orderForm.value.state==""){
      alert("Stato non definito")
      bool=false;
    }
    if(bool&&orderForm.value.country==""){
      alert("Provincia non definito")
      bool=false;
    }
    if(bool&&orderForm.value.zipcode==""){
      alert("codice postale non definito")
      bool=false;
    }
    if(bool&&orderForm.value.phone==""){
      alert("telefono non definito")
      bool=false;
    }
    if(bool&&orderForm.value.email==""){
      alert("Email non definita")
      bool=false;
    }
    if(bool&&orderForm.value.nome==""){
      alert("Nome non definito")
      bool=false;
    }
    if(bool&&orderForm.value.cognome==""){
      alert("Cognome non definito")
      bool=false;
    }
    if(bool&&CreditCard.nomecard==""){
      alert("Nome del titolare della carta non definita")
      bool=false;
    }
    if(bool&&CreditCard.cognomecard==""){
      alert("Cognome del titolare della carta della carta non definita")
      bool=false;
    }
    if(bool&&(CreditCard.creditnumber==""||CreditCard.creditnumber.length<16||CreditCard.creditnumber.length>19)){
      alert("Numero della carta non definito o non valido")
      bool=false;
    }
    if(bool&&(CreditCard.creditcode==""||CreditCard.creditcode.length!=3)){
      alert("Codice della carta non definito o non valido")
      bool=false;
    }
    if(bool&&(CreditCard.expiration_date==""||CreditCard.expiration_date.length>7||CreditCard.expiration_date.length<5)){
      alert("Data della carta non definito o non valido")
      bool=false;
    }
    return bool;
  }

  public controlla_prodotti(lista_prod:DettaglioCarrello[]):boolean{
    let verify:boolean=true;
    for (let i=0; i<lista_prod.length;i++){
      if(lista_prod[i].quantity>lista_prod[i].product.quantity){
        verify=false;
      }
    }
    return verify;
  }

  public clearcar(lista_prod: DettaglioCarrello[], placeorder:PlaceOrder) {
    for (let i=0; i<lista_prod.length;i++){
      const dettaglioordine:DettaglioOrdine={id:0,placeorder:placeorder,product:lista_prod[i].product,quantity:lista_prod[i].quantity}
      this.api.registerDetailOrder(dettaglioordine).subscribe((det)=>{
        console.log(det)
        this.delete(lista_prod[i]);
        var Product:Product=det.product;
        Product.quantity=Product.quantity-det.quantity;
        this.api.updateProduct(Product).subscribe((prod)=>{
          this.route.navigate(['/home']);})
      });
      
    }
  }

}


