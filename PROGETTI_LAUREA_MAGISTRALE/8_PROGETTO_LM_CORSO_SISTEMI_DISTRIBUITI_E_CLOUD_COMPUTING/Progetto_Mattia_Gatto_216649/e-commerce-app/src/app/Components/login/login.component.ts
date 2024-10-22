import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../../model/user';
import { ApiService } from '../../service/api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],

})
export class LoginComponent implements OnInit {
  
  loginForm: any;
  private invalidLogin = false;
  constructor(private apiService: ApiService,private router: Router, private formBuilder: FormBuilder) {
    this.createForm();
  }
  ngOnInit() {
    
  }

  createForm() {
    this.loginForm = this.formBuilder.group({
      email: '',
      password: '',
      username: '',
      age: 0,
      usertype: 'user',
      nome:'',
      cognome:'',
      jobTitle:'',
      phone:'',
      imageUrl:'',
      userCode:'',
      address:null,
      carrello:null,
    });
  }
  public login(): void {
    let password=this.loginForm.value.password;
    let email=this.loginForm.value.email;
    this.apiService.userLogin(email,password).subscribe(
      (response: any) => {
        let x=sessionStorage.getItem('email');
        this.apiService.storeToken(response.email);
        this.invalidLogin=false;
        console.log(response);        
        this.router.navigate(['/home']);
      },(error: HttpErrorResponse) => {
          this.apiService.adminLogin(email,password).subscribe(
          (response: any) => {
            let x=sessionStorage.getItem('email');
            this.apiService.storeToken(response.email);
            this.invalidLogin=false;
            console.log(response);
            this.router.navigate(['/admin']);
          },(error: HttpErrorResponse) => {
            this.invalidLogin=true;
            alert("utente non trovato");
            this.loginForm.reset();
          }
      )}
    );
  }
  
  
}
