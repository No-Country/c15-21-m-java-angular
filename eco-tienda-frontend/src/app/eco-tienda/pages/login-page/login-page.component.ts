import { Component } from '@angular/core';
import { FormBuilder,FormGroup  } from '@angular/forms';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})

  
export class LoginPageComponent {

  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      email: ['email@gmail.com'],
      password: [''],
    });
  }


}