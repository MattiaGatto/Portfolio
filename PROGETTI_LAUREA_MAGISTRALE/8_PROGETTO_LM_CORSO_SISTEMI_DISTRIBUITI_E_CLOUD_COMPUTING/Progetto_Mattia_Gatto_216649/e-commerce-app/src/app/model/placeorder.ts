import { Address } from "./address";
import { CreditCard } from "./creditcard";
import { User } from "./user";


export interface PlaceOrder{
    id:number;
    email:String;
    orderStatus:String;
    orderDate:Date;
    totalCost:number;
    address:String;
    city:String;
    state:String;
    country:String;
    zipcode:number;
    phonenumber:String;
    nome:String;
    cognome:String;
    description:String;
    creditCard:CreditCard;
    useremail:String;
}