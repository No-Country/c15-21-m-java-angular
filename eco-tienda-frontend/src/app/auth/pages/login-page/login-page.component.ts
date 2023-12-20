import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../../services/auth.service';

import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent {
  private fb = inject(FormBuilder);

  private authService = inject(AuthService);

  private toastr = inject(ToastrService);

  private router = inject(Router);

  public myForm: FormGroup = this.fb.group({
    email: ['david@david.com', [Validators.required, Validators.email]],
    password: ['david', [Validators.required, Validators.minLength(6)]],
  });

  login() {
    console.log(this.myForm.value);
    const { email, password } = this.myForm.value;
    this.authService.login(email, password).subscribe({
      next: () => {
        this.router.navigateByUrl('/eco-tienda');
      },
      error: (error) => {
        this.toastr.warning(`${error}`, '', {
          progressBar: true,
        });

        console.log({ loginError: error });
      },
    });
  }
}
