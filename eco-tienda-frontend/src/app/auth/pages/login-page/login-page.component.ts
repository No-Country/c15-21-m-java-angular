import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../../services/auth.service';

import { ToastrService } from 'ngx-toastr';
import { ValidatorsService } from '../../../services/validators.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent {
  private fb = inject(FormBuilder);

  private authService = inject(AuthService);
  private validatorsService = inject(ValidatorsService);

  private toastr = inject(ToastrService);

  private router = inject(Router);
  public showPassword: boolean = false;
  public type: string = "password";

  public loginForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(4)]],
  });

  isValidField(field: string) {
    return this.validatorsService.isValidField(this.loginForm, field);
  }

  login() {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const { email, password } = this.loginForm.value;
    this.authService.login(email, password).subscribe({
      next: () => {
        this.loginForm.reset();
        this.router.navigateByUrl('/eco-tienda');
      },
      error: (error) => {
        this.loginForm.reset();
        this.toastr.warning(`${error}`, '', {
          progressBar: true,
        });
      },
    });
  }

  PasswordToggle(){
    this.showPassword = !this.showPassword;
    this.type = this.type == "password" ? "text" : "password";
  }
  
}
