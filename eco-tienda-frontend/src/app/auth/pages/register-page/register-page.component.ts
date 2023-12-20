import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { ValidatorsService } from '../../../services/validators.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css'],
})
export class RegisterPageComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private validatorsService = inject(ValidatorsService);
  private toastr = inject(ToastrService);
  private router = inject(Router);

  public registerForm: FormGroup = this.fb.group(
    {
      fullName: [
        '',
        [
          Validators.required,
          Validators.pattern(
            this.validatorsService.firstNameAndLastnamePattern
          ),
        ],
      ],
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(this.validatorsService.emailPattern),
        ],
      ],
      password: ['', [Validators.required, Validators.minLength(6)]],
      password2: ['', [Validators.required, Validators.minLength(6)]],
      termsAndConditions: [false, Validators.requiredTrue],
    },
    {
      validators: [
        this.validatorsService.isFieldOneEqualFieldTwo('password', 'password2'),
      ],
    }
  );

  isValidField(field: string) {
    return this.validatorsService.isValidField(this.registerForm, field);
  }

  register() {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }
    const { fullName, email, password } = this.registerForm.value;
    console.log(this.registerForm.value);

    this.authService.register(fullName, email, password).subscribe({
      next: () => {
        this.toastr.success(
          `Tu cuenta ha sido creada con éxito.`,
          '¡Bienvenido!',
          { progressBar: true }
        );
        this.registerForm.reset();
      },
      error: (error) => {
        this.toastr.warning(`${error}`, '', {
          progressBar: true,
        });
        this.registerForm.reset();
      },
    });
  }
}
