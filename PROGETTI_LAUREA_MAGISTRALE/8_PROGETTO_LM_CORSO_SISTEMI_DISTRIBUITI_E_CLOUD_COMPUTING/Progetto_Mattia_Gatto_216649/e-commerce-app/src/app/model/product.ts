import { Carrello } from "./carrello";

export interface Product{
    id:number;
    name:String;
    description:String;
    imageurl:String;
    price:any;
    quantity:number;
    carrello:Carrello;

}