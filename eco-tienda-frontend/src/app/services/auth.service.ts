import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { Observable, map, tap } from 'rxjs';
import { AuthStatus } from '../interfaces/auth-status.enum';
import { LoginResponse } from '../interfaces/login.interface';
import { environments } from 'src/environments/environments';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl: string = environments.baseUrl;

  private http = inject(HttpClient);

  private _authStatus = signal<AuthStatus>(AuthStatus.checking);

  login(email: string, password: string): Observable<boolean> {
    const url = `${this.baseUrl}/v1/iniciarSesion`;
    const body = { email, password };

    return this.http.post<LoginResponse>(url, body).pipe(
      tap(({ token }) => {
        this._authStatus.set(AuthStatus.authenticated);
        localStorage.setItem('token', token);
      }),
      map(() => true)
    );
  }
}
