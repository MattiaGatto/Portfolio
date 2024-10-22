import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from './model/user';
import { ApiService } from './service/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'e-commerce-app';
  currentUser!: User;
  constructor(private auth: ApiService, private router: Router) {
  }
  ngOnInit() { }

}
