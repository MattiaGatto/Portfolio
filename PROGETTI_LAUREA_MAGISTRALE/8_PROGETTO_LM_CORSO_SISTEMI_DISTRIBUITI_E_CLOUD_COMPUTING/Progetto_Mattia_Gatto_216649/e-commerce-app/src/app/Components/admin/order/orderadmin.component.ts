import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { DettaglioOrdine } from '../../../model/dettaglioordine';
import { PlaceOrder } from '../../../model/placeorder';
import { ApiService } from '../../../service/api.service';

@Component({
  selector: 'app-orderadmin',
  templateUrl: './orderadmin.component.html',
  styleUrls: ['./orderadmin.component.css']
})
export class OrderAdminComponent implements OnInit {

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
    this.apiService.getPlaceOrder().subscribe(
      (response: PlaceOrder[]) => {
        this.ordini=response;
        this.apiService.getDetailOrder().subscribe((det)=>{
              this.dettagliordini=det;
            }
          )
        },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }
  

  

}
