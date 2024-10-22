import { PlaceOrder } from "./placeorder";
import { Product } from "./product";

export interface DettaglioOrdine{
    id:number;
    placeorder:PlaceOrder;
    product:Product;
    quantity:any;
}