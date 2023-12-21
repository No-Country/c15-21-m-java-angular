import { HttpClient } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';
import { Observable, catchError, map, of, tap, throwError } from 'rxjs';
import { AuthStatus } from '../interfaces/auth-status.enum';
import { LoginResponse } from '../interfaces/login.interface';
import { environments } from 'src/environments/environments';
import { EcoTiendaService } from './eco-tienda.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl: string = environments.baseUrl;

  private http = inject(HttpClient);

  private _authStatus = signal<AuthStatus>(AuthStatus.checking);

  public authStatus = computed(() => this._authStatus());

  private tiendaService = inject(EcoTiendaService);

  public shoppingCart: any = [];

  public ShoppingCartId: number = 0;

  constructor() {
    this.checkAuthStatus().subscribe();
  }

  login(email: string, password: string): Observable<boolean> {
    const url = `${this.baseUrl}/v1/iniciarSesion`;
    const body = { email, password };

    return this.http.post<LoginResponse>(url, body).pipe(
      tap(({ token }) => {
        this._authStatus.set(AuthStatus.authenticated);
        localStorage.setItem('token', token);
        localStorage.setItem('email', email);
        this.obtenerShoppingCartByEMail(email);
      }),
      map(() => true),
      catchError((err) => {
        console.log(err);

        return throwError(() => 'Sus credenciales no son válidas');
      })
    );
  }

  checkAuthStatus(): Observable<boolean> {
    const token = localStorage.getItem('token');
    if (!token) {
      this.logout();
      return of(false);
    }
    return of(true);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    localStorage.removeItem('cartId');
    this._authStatus.set(AuthStatus.notAuthenticated);
  }

  register(name: string, email: string, password: string): Observable<boolean> {
    const url = `${this.baseUrl}/v1/registrarse`;
    const body = { name, email, password };

    return this.http.post<LoginResponse>(url, body).pipe(
      tap(({ token }) => {
        this._authStatus.set(AuthStatus.authenticated);
        localStorage.setItem('token', token);
      }),
      map(() => true),
      catchError((err) => {
        console.log(err);

        return throwError(() => 'Sus credenciales no son válidas');
      })
    );
  }

  obtenerShoppingCartByEMail(email: String) {
    this.tiendaService.getShoppingCartByEmail(email).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart;
        console.log(this.shoppingCart)
        this.ShoppingCartId = shoppingCart.cartId;
        console.log(this.ShoppingCartId)
        localStorage.setItem('cartId', String(this.ShoppingCartId));
      }
    })
  }
}
