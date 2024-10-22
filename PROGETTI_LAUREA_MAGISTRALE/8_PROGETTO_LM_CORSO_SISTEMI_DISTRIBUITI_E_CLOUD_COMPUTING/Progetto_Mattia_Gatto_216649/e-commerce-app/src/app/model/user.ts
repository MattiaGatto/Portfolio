import { Address } from "./address";
import { Carrello } from "./carrello";
import { CreditCard } from "./creditcard";

export interface User{
    id:number;
    nome:String;
    cognome:String;
    age:number;
    username:String;
    password:String;
    usertype:String;
    email:String;
    jobTitle:String;
    phone:String;
    imageUrl:any;
    userCode:String;
    address:Address;
    carrello:Carrello;
}