import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  registerForm: any;
  addressForm:any;
  auth!: string;
  userinfo: String[]=[];
  addressinfo: String[]=[];

  constructor(private apiService: ApiService,private router: Router,
    private formBuilder: FormBuilder) {
    this.getinfo();
    this.createFormAddress();
    this.createFormUser();
    
    
  }

  ngOnInit() {
    
  }

  getinfo(){
    this.auth = sessionStorage.getItem('email') as string;
    this.apiService.userGetEmail(this.auth).subscribe((user)=>{
      this.userinfo[0]=user.password;
      this.userinfo[1]=user.username;
      this.userinfo[2]=user.age as unknown as String;
      this.userinfo[3]=user.nome;
      this.userinfo[4]=user.cognome;
      this.userinfo[5]=user.jobTitle;
      this.userinfo[6]=user.phone;
      this.apiService.getAddressId(user.address.id).subscribe((address)=>{
        this.addressinfo[0]=address.address;
        this.addressinfo[1]=address.city;
        this.addressinfo[2]=address.state ;
        this.addressinfo[3]=address.country;
        this.addressinfo[4]=address.zipcode as unknown as String;
        this.addressinfo[5]=address.phonenumber;
        this.addressinfo[6]=address.email;
        this.addressinfo[7]=address.nome;
        this.addressinfo[8]=address.cognome;
      });
    });
  }

  createFormUser() {
    this.registerForm = this.formBuilder.group({
      email: this.auth,
      password:  this.userinfo[0],
      username: this.userinfo[1],
      age:  this.userinfo[2],
      nome: this.userinfo[3],
      cognome: this.userinfo[4],
      jobTitle: this.userinfo[5],
      phone: this.userinfo[6]
    });
  }
  createFormAddress() {
    this.addressForm = this.formBuilder.group({
      address: this.addressinfo[0],
      city:  this.addressinfo[1],
      state:  this.addressinfo[2],
      country:  this.addressinfo[3],
      zipcode:  this.addressinfo[4],
      phone:  this.addressinfo[5],
      email:this.addressinfo[6],
      nome:this.addressinfo[7],
      cognome:this.addressinfo[8],
    });
  }

  controllaUser(registerForm: NgForm){
    if(registerForm.value.password==""){
      registerForm.value.password=this.userinfo[0] as string;
    }
    if(registerForm.value.username==""){
      registerForm.value.username=this.userinfo[1] as string;
    }
    if(registerForm.value.age==""){
      registerForm.value.age=this.userinfo[2] as string;
    }
    if(registerForm.value.nome==""){
      registerForm.value.nome=this.userinfo[3] as string;
    }
    if(registerForm.value.cognome==""){
      registerForm.value.cognome=this.userinfo[4] as string;
    }
    if(registerForm.value.jobTitle==""){
      registerForm.value.jobTitle=this.userinfo[5] as string;
    }
    if(registerForm.value.phone==""){
      registerForm.value.phone=this.userinfo[6] as string;
    }
  }

  controllaAddress(addressForm: NgForm){
    if(addressForm.value.address==""){
      addressForm.value.address=this.addressinfo[0] as string;
    }
    if(addressForm.value.city==""){
      addressForm.value.city=this.addressinfo[1] as string;
    }
    if(addressForm.value.state==""){
      addressForm.value.state=this.addressinfo[2] as string;
    }
    if(addressForm.value.country==""){
      addressForm.value.country=this.addressinfo[3] as string;
    }
    if(addressForm.value.zipcode==""){
      addressForm.value.zipcode=this.addressinfo[4] as string;
    }
    if(addressForm.value.phone==""){
      addressForm.value.phone=this.addressinfo[5] as string;
    }
    if(addressForm.value.email==""){
      addressForm.value.email=this.addressinfo[6] as string;
    }
    if(addressForm.value.nome==""){
      addressForm.value.nome=this.addressinfo[7] as string;
    }
    if(addressForm.value.cognome==""){
      addressForm.value.cognome=this.addressinfo[8] as string;
    }
  }

  public updateUser(registerForm: NgForm): void {
    this.auth = sessionStorage.getItem('email') as string;
    this.apiService.userGetEmail(this.auth).subscribe((old_user)=>{
      this.getinfo();
      this.controllaUser(registerForm);
      old_user.age=registerForm.value.age;
      old_user.password=registerForm.value.password;
      old_user.username=registerForm.value.username;
      old_user.nome=registerForm.value.nome;
      old_user.cognome=registerForm.value.cognome;
      old_user.jobTitle=registerForm.value.jobTitle;
      old_user.phone=registerForm.value.phone;
      if(this.verificaformUser(registerForm)){
        this.apiService.updateUser(old_user).subscribe(
          (response: any) => { 
            console.log(response);
            this.getinfo();
            },(error: HttpErrorResponse) => {
              alert("controlla le informazioni");
          }
        );
      }else{
        //alert("informazioni errate")
      }
    });
  }

  public updateAddress(addressForm: NgForm): void {
    this.auth = sessionStorage.getItem('email') as string;
    this.apiService.userGetEmail(this.auth).subscribe((user)=>{
      this.apiService.getAddressId(user.address.id).subscribe((old_address)=>{
        this.getinfo();
        this.controllaAddress(addressForm);
        old_address.city=addressForm.value.city;
        old_address.address=addressForm.value.address;
        old_address.country=addressForm.value.country;
        old_address.phonenumber=addressForm.value.phone;
        old_address.state=addressForm.value.state;
        old_address.zipcode=addressForm.value.zipcode;
        old_address.nome=addressForm.value.nome;
        old_address.cognome=addressForm.value.cognome;
        if(this.verificaAddress(addressForm)){
          this.apiService.updateAddress(old_address).subscribe(
            (response: any) => { 
              console.log(response);
              this.getinfo()
              },(error: HttpErrorResponse) => {
                alert("controlla le informazioni");
            }
          );
        }else{
          //alert("info sbagliate")
        }
      });
    });
  }

  public verificaformUser(registerForm:NgForm){
    let bool=true
   
    
    var password = new RegExp(/\w{4,12}/i);
    if(bool&& !password.test(registerForm.value.password)){
      alert("password errata")
      bool=false
    }
    var username = new RegExp(/\w{4,10}/i);
    if(bool&& !username.test(registerForm.value.username)){
      alert("username errata")
      bool=false
    }
    var nome = new RegExp(/[a-z \s]{2,15}/i);
    if(bool&&! nome.test(registerForm.value.nome)){
      alert("nome errato")
      bool=false
    }
    var cognome = new RegExp(/[a-z \s]{2,15}/i);
    if(bool&&! cognome.test(registerForm.value.cognome)){
      alert("cognome errato")
      bool=false
    }
    var phone = new RegExp(/^[+]*[(]{0,1}[0-9]{1,3}[)]{0,1}[-\s\./0-9]*$/g)
    if(bool&&! phone.test(registerForm.value.phone)){
      alert("phone number errato")
      bool=false
    }
    var age = new RegExp(/[0-9]{0,3}/);
    if(bool&&! age.test(registerForm.value.age) && registerForm.value.age>8){
      alert("age errata")
      bool=false
    }
    return bool;
  }
  
  public verificaAddress(addressForm:NgForm){
    let bool=true
   
    var nome = new RegExp(/[a-z \s]{2,15}/i);
    if(bool&&! nome.test(addressForm.value.nome)){
      alert("nome errato")
      bool=false
    }
    var cognome = new RegExp(/[a-z \s]{2,15}/i);
    if(bool&&! cognome.test(addressForm.value.cognome)){
      alert("cognome errato")
      bool=false
    }
    var zipcode = new RegExp(/[0-9]{5}/g)
    if(bool&&! zipcode.test(addressForm.value.zipcode)){
      alert("zipcode number errato")
      bool=false
    }
    return bool;
  }

  
}
