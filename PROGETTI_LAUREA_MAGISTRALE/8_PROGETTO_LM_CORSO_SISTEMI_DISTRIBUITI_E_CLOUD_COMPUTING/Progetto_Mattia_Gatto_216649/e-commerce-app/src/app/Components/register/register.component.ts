import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Carrello } from 'src/app/model/carrello';
import { PlaceOrder } from 'src/app/model/placeorder';
import { User } from '../../model/user';
import { ApiService } from '../../service/api.service';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  iduser:number=0;
  registerForm: any;
  invalidLogin = false;
  
  constructor(private apiService: ApiService,private router: Router,
    private formBuilder: FormBuilder) {
    this.createForm();
  }

  ngOnInit() {}

  createForm() {
    this.registerForm = this.formBuilder.group({
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
  public createUser(): void {
    
    console.log(this.registerForm.value)
    
    if(this.verificaformUser()){
      this.apiService.registerUser(this.registerForm.value).subscribe(
        (response: any) => { 
          let x=sessionStorage.getItem('email');
          this.apiService.storeToken(response.email);
          this.invalidLogin=false;
          console.log(response);
          if(response.usertype=="user"){
            this.router.navigate(['/home']);
          }else if(response.usertype=="admin"){
            this.router.navigate(['/admin']);
          }
          },(error: HttpErrorResponse) => {
            this.invalidLogin=true;
            alert("controlla le informazioni, email già esistente!");
            this.registerForm.reset();
        }
      );
    }
    else{
      this.registerForm.reset();
    }
  }

  public verificaformUser(){
    let bool=true
    var email = new RegExp(/\w+@\w+\.\w{2,4}/i);
    if(! email.test(this.registerForm.value.email)){
      alert("email errata")
      bool=false
    }
    var password = new RegExp(/\w{4,12}/i);
    
    if(bool&& !password.test(this.registerForm.value.password)){
      alert("password errata")
      bool=false
    }
    var username = new RegExp(/\w{4,10}/i);
    if(bool&& !username.test(this.registerForm.value.username)){
      alert("username errata")
      bool=false
    }
    var nome = new RegExp(/[a-z]{2,15}/i);
    if(bool&&! nome.test(this.registerForm.value.nome)){
      alert("nome errato")
      bool=false
    }
    var cognome = new RegExp(/[a-z]{2,15}/i);
    if(bool&&! cognome.test(this.registerForm.value.cognome)){
      alert("cognome errato")
      bool=false
    }
    var phone = new RegExp(/^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\s\./0-9]*$/g)
    if(bool&&! phone.test(this.registerForm.value.phone)){
      alert("phone number errato")
      bool=false
    }
    var age = new RegExp(/[0-9]{0,3}/);
    if(bool&&! age.test(this.registerForm.value.age) && (this.registerForm.value.age<8||this.registerForm.value.age>110)){
      alert("age errata")
      bool=false
    }
    return bool;
  }

}

