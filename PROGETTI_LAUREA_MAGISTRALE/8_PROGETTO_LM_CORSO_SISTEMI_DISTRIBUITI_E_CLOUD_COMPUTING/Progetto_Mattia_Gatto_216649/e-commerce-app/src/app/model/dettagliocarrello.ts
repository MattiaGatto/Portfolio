import { Carrello } from "./carrello";
import { Product } from "./product";


export interface DettaglioCarrello{
    id:number;
    carrello:Carrello;
    product:Product;
    quantity:number;
}