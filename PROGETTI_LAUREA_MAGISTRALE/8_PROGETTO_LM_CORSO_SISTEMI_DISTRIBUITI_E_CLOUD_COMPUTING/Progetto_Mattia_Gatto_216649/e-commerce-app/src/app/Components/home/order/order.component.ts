import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { DettaglioOrdine } from '../../../model/dettaglioordine';
import { PlaceOrder } from '../../../model/placeorder';
import { ApiService } from '../../../service/api.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  token!:string;
  placeorder:any;
  dettagliordini: Array<DettaglioOrdine> = []
  ordini: Array<PlaceOrder> = []
  

  constructor(private apiService: ApiService,private router: Router,private formBuilder: FormBuilder) {}

  ngOnInit() {
    if(this.apiService.isUserLoggedIn()){
      this.token=sessionStorage.getItem('email') as string;
      this.getOrdini()
    }
  }

  public getOrdini(): void {
    this.apiService.getPlaceOrderByUserEmail(this.token).subscribe(
      (response: PlaceOrder[]) => {
        for(let i=0;i<response.length;++i){
          this.ordini.push(response[i]);
          this.apiService.getDetailOrderbyPlaceorder(response[i]).subscribe((det)=>{
            for(let j=0;j<det.length;++j){
              this.dettagliordini.push(det[j]);
            }
          })
          }
        },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );console.log(this.dettagliordini)
  }
  

  

}
