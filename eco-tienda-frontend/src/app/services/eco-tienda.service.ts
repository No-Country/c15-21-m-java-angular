import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environments } from '../../environments/environments';
import { Observable } from 'rxjs';
import { ProductsResponse } from '../interfaces/products.interface';
import { CategoriesResponse } from '../interfaces/categories.interface';
import { ShoppingCartResponse } from '../interfaces/shoping-cart.interface';
import { DetailResponse } from '../interfaces/detail.interface';

@Injectable({
  providedIn: 'root',
})
export class EcoTiendaService {
  private url: string = environments.baseUrl;

  private http = inject(HttpClient);

  getProducts(): Observable<ProductsResponse[]> {
    return this.http.get<ProductsResponse[]>(
      `${this.url}/administrador/productos`
    );
  }

  getCategories(): Observable<CategoriesResponse[]> {
    return this.http.get<CategoriesResponse[]>(`${this.url}/v1/categorias`);
  }
  searchProductDetail(id: string): Observable<DetailResponse> {
    return this.http.get<DetailResponse>(
      `${this.url}/administrador/productos/${id}`
    );
  }
  getShoppingCart(): Observable<ShoppingCartResponse[]> {
    return this.http.get<ShoppingCartResponse[]>(`${this.url}/api/cart/admin/carts/2`);
  }
  createShoppingCart(email: String): Observable<ShoppingCartResponse[]> {
    return this.http.get<ShoppingCartResponse[]>(`${this.url}/api/cart/public/carts/user/${email}?email=${email}`);
  }
  addToShoppingCart(cart: Number,product: Number,quantity: Number): Observable<ShoppingCartResponse[]> {
    return this.http.get<ShoppingCartResponse[]>(`${this.url}api/cart/public/carts/${cart}/products/${product}/quantity/${quantity}?cartId=${cart}&productId=${product}&quantity=${quantity}`);
  }
}
