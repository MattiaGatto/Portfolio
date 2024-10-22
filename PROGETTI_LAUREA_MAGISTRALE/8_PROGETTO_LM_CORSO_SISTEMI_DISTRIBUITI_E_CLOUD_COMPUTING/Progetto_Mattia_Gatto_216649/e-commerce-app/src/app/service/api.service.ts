import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { environment } from 'src/environments/environment';
import { Product } from '../model/product';
import { Address } from '../model/address';
import { PlaceOrder } from '../model/placeorder';
import { Carrello } from '../model/carrello';
import { SESSION_STORAGE, StorageService } from 'ngx-webstorage-service';
import { DettaglioCarrello } from '../model/dettagliocarrello';
import { CreditCard } from '../model/creditcard';
import { DettaglioOrdine } from '../model/dettaglioordine';

@Injectable({providedIn: 'root'})

export class ApiService {
 
  private apiServerUrl = environment.apiBaseUrl;

  constructor( @Inject(SESSION_STORAGE) private storage: StorageService, private http: HttpClient){}

  public getUser(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiServerUrl}/user/user/all`);
  }

  // Registering the users to the database
  public registerUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiServerUrl}/user/user/add`, user);
  }

  //update user
  public updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.apiServerUrl}/user/user/update`, user);
  }

  // delete user
  public deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/user/delete/${userId}`);
  }
  
  // validating user credentials
  public userLogin(email: String,password: String): Observable<User> {
    let usertype:String="user";
    return this.http.get<User>(`${this.apiServerUrl}/user/user/findu/${email}/${password}/${usertype}`);
  }

  // validating user credentials
  public userGet(userId: number): Observable<User> {
    return this.http.get<User>(`${this.apiServerUrl}/user/user/find/${userId}`);
  }

  // validating admin credentials
  public adminLogin(email: String,password: String): Observable<User> {
    let usertype:String="admin";
    return this.http.get<User>(`${this.apiServerUrl}/user/user/findu/${email}/${password}/${usertype}`);
  }

  // get user by email
  public userGetEmail(email: String): Observable<User> {
    let usertype:String="user";
    return this.http.get<User>(`${this.apiServerUrl}/user/user/finde/${email}/${usertype}`);
  }

  // Fetching all the products from the database
  public getProduct(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiServerUrl}/user/product/all`);
  }

  // Fetching the products from the database
  public getProductId(productid: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiServerUrl}/user/product/find/${productid}`);
  }

  // Add the new product  in the database
  public registerProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.apiServerUrl}/user/product/add`, product);
  }

  //update product
  public updateProduct(product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiServerUrl}/user/product/update`, product);
  }

  // delete product
  public deleteProduct(productid: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/product/delete/${productid}`);
  }

  // Fetching all the address from the database
  public getAddress(): Observable<Address[]> {
    return this.http.get<Address[]>(`${this.apiServerUrl}/user/address/all`);
  }

  // Fetching the Address from the database
  public getAddressId(addresstid: number): Observable<Address> {
    return this.http.get<Address>(`${this.apiServerUrl}/user/address/find/${addresstid}`);
  }

  // Add the new Address  in the database
  public registerAddress(address: Address): Observable<Address> {
    return this.http.post<Address>(`${this.apiServerUrl}/user/address/add`, address);
  }

  //update Address
  public updateAddress(address: Address): Observable<Address> {
    return this.http.put<Address>(`${this.apiServerUrl}/user/address/update`, address);
  }

  // delete Address
  public deleteAddress(addressid: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/address/delete/${addressid}`);
  }

  // Fetching all the placeorder from the database
  public getPlaceOrder(): Observable<PlaceOrder[]> {
    return this.http.get<PlaceOrder[]>(`${this.apiServerUrl}/user/placeorder/all`);
  }

  // Fetching the PlaceOrder from the database
  public getPlaceOrderId(placeorderid: number): Observable<PlaceOrder> {
    return this.http.get<PlaceOrder>(`${this.apiServerUrl}/user/placeorder/find/${placeorderid}`);
  }

  // Fetching the PlaceOrder from the database
  public getPlaceOrderByUserEmail(email: String): Observable<PlaceOrder[]> {
    return this.http.get<PlaceOrder[]>(`${this.apiServerUrl}/user/placeorder/finde/${email}`);
  }

  // Add the new placeorder  in the database
  public registerPlaceOrder(placeorder: PlaceOrder): Observable<PlaceOrder> {
    return this.http.post<PlaceOrder>(`${this.apiServerUrl}/user/placeorder/add`, placeorder);
  }

  //update placeorder
  public updatePlaceOrder(placeorder: PlaceOrder): Observable<PlaceOrder> {
    return this.http.put<PlaceOrder>(`${this.apiServerUrl}/user/placeorder/update`, placeorder);
  }

  // delete placeorder
  public deletePlaceOrder(placeorderid: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/placeorder/delete/${placeorderid}`);
  }

  // Fetching all the Cart from the database
  public getCart(): Observable<Carrello[]> {
    return this.http.get<Carrello[]>(`${this.apiServerUrl}/user/carrello/all`);
  }

  // Fetching the Cart from the database
  public getCartId(carrelloid: number): Observable<Carrello> {
    return this.http.get<Carrello>(`${this.apiServerUrl}/user/carrello/find/${carrelloid}`);
  }

  // Fetching the Cart from the database with email
  public getCartEmail(email: String): Observable<Carrello> {
    return this.http.get<Carrello>(`${this.apiServerUrl}/user/carrello/finde/${email}`);
  }

  // Fetching the Cart from the database with email and id
  public getCartIdEmail(carrelloid: number, email:String): Observable<Carrello> {
    return this.http.get<Carrello>(`${this.apiServerUrl}/user/carrello/findc/${carrelloid}/${email}`);
  }

  // Add the new Cart  in the database
  public registerCart(carrello: Carrello): Observable<Carrello> {
    return this.http.post<Carrello>(`${this.apiServerUrl}/user/carrello/add`, carrello);
  }

  //update Cart
  public updateCart(carrello: Carrello): Observable<Carrello> {
    return this.http.put<Carrello>(`${this.apiServerUrl}/user/carrello/update`, carrello);
  }

  //update Cart product
  public updateCartProducts(carrello: Carrello,product :number): Observable<Carrello> {
    return this.http.put<Carrello>(`${this.apiServerUrl}/user/carrello/updateProd/${product}`,carrello);
  }

  // delete Cart by id
  public deleteCartId(carrelloid: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/carrello/delete/${carrelloid}`);
  }
  // delete Cart
  public deleteCartIdEmail(carrelloid: number, email: String): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/carrello/delete/${carrelloid}/${email}`);
  }

  // Fetching all the detail from the database
  public getDetail(): Observable<DettaglioCarrello[]> {
    return this.http.get<DettaglioCarrello[]>(`${this.apiServerUrl}/user/dettagliocarrello/all`);
  }
  public getDetailbyCart(carrello:Carrello): Observable<DettaglioCarrello[]> {
    return this.http.post<DettaglioCarrello[]>(`${this.apiServerUrl}/user/dettagliocarrello/findc`,carrello);
  }

  // Fetching detail from the database by id
  public getDetailbyId(id:number): Observable<DettaglioCarrello> {
    return this.http.get<DettaglioCarrello>(`${this.apiServerUrl}/user/dettagliocarrello/find/${id}`);
  }

  // Add the new Cart  in the database
  public registerDetail(dettagliocarrello: DettaglioCarrello): Observable<DettaglioCarrello> {
    return this.http.post<DettaglioCarrello>(`${this.apiServerUrl}/user/dettagliocarrello/add`, dettagliocarrello);
  }

  //update Cart
  public updateDetail(dettagliocarrello: DettaglioCarrello): Observable<DettaglioCarrello> {
    return this.http.put<DettaglioCarrello>(`${this.apiServerUrl}/user/dettagliocarrello/update`, dettagliocarrello);
  }

  // delete Cart by id
  public deleteDetail(detid: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/dettagliocarrello/delete/${detid}`);
  }

  // Fetching all the CreditCard from the database
  public getCreditCard(): Observable<CreditCard[]> {
    return this.http.get<CreditCard[]>(`${this.apiServerUrl}/user/creditcard/all`);
  }

  // Fetching the CreditCard from the database
  public getCreditCardbyId(creditcardid: number): Observable<CreditCard> {
    return this.http.get<CreditCard>(`${this.apiServerUrl}/user/creditcard/find/${creditcardid}`);
  }

  // Fetching the CreditCard from the database
  public getCreditCardbyEmail(email: String): Observable<CreditCard> {
    return this.http.get<CreditCard>(`${this.apiServerUrl}/user/creditcard/findce/${email}`);
  }

   // Fetching the CreditCard from the database
   public getCreditCardbyNumberCodeData(codenumber: String,code:String): Observable<CreditCard> {
    return this.http.get<CreditCard>(`${this.apiServerUrl}/user/creditcard/findfindcc/${codenumber}/${code}`);
  }

  // Add the new CartCredit  in the database
  public registerCreditCard(creditcard: CreditCard): Observable<CreditCard> {
    return this.http.post<CreditCard>(`${this.apiServerUrl}/user/creditcard/add`, creditcard);
  }

  //update CreditCard
  public updateCreditCard(creditcard: CreditCard): Observable<CreditCard> {
    return this.http.put<CreditCard>(`${this.apiServerUrl}/user/creditcard/update`, creditcard);
  }

  // delete CreditCard
  public deleteCreditCard(CreditCardid: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/creditcard/delete/${CreditCardid}`);
  }
  // Fetching all the detail from the database
  public getDetailOrder(): Observable<DettaglioOrdine[]> {
    return this.http.get<DettaglioOrdine[]>(`${this.apiServerUrl}/user/dettaglioordine/all`);
  }
  public getDetailOrderbyPlaceorder(placeorder:PlaceOrder): Observable<DettaglioOrdine[]> {
    return this.http.post<DettaglioOrdine[]>(`${this.apiServerUrl}/user/dettaglioordine/findp`,placeorder);
  }

  // Fetching detail from the database by id
  public getDetailOrderbyId(id:number): Observable<DettaglioOrdine> {
    return this.http.get<DettaglioOrdine>(`${this.apiServerUrl}/user/dettaglioordine/find/${id}`);
  }

  // Add the new Cart  in the database
  public registerDetailOrder(dettaglioordine: DettaglioOrdine): Observable<DettaglioOrdine> {
    return this.http.post<DettaglioOrdine>(`${this.apiServerUrl}/user/dettaglioordine/add`, dettaglioordine);
  }

  //update Cart
  public updateDetailOrder(dettaglioordine: DettaglioOrdine): Observable<DettaglioOrdine> {
    return this.http.put<DettaglioOrdine>(`${this.apiServerUrl}/user/dettaglioordine/update`, dettaglioordine);
  }

  // delete Cart by id
  public deleteDetailOrder(detid: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/dettaglioordine/delete/${detid}`);
  }



  



  
  // Authentication Methods 

  

  /*public isUserLoggedIn(): boolean {
    console.log(!(this.getToken() === null))
    return this.getToken() !== null;
  }

  storeToken(token: string) {
    this.storage.set("auth_token", token);
  }

  getToken() {
    return this.storage.get("auth_token");
  }

  logOut() {
    return this.storage.remove("auth_token");
  }*/
    
  isUserLoggedIn() {
    let user = sessionStorage.getItem('user')
    console.log(!(user === null))
    return !(user === null)
  }

  logOut() {
    sessionStorage.removeItem('user');
    let user = sessionStorage.getItem('user')
    console.log(!(user === null))
  }

  getToken() {
    return sessionStorage.getItem('user');
  }
  storeToken(token: string) {
    sessionStorage.setItem("user", token);
  }
  
}