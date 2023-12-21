import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environments } from '../../environments/environments';
import { Observable } from 'rxjs';
import { ProductsResponse } from '../interfaces/products.interface';
import { CategoriesResponse } from '../interfaces/categories.interface';
import { ShoppingCartResponse ,ShoppingCartIdResponse } from '../interfaces/shoping-cart.interface';
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

  //*********CARRITO *****************
  editProductShoppingCart(cartId: Number,productId: Number,quantity: Number): Observable<any> {
    return this.http.put(`${this.url}/api/cart/public/carts/${cartId}/products/${productId}/quantity/${quantity}`,{cartId: cartId, productId: productId, quantity: Number});
  }
  addToShoppingCart(cart: Number,product: Number,quantity: Number): Observable<any> {
    return this.http.post(`${this.url}/api/cart/public/carts/${cart}/products/${product}/quantity/${quantity}?cartId=${cart}&productId=${product}&quantity=${quantity}`,{cart: Number,product: Number,quantity: Number});
  }
  createShoppingCart(email: String): Observable<any> {
    return this.http.post(`${this.url}/api/cart/public/carts/user/${email}?email=${email}`,{email: email});
  } 


  
  getShoppingCartId(id: Number): Observable<ShoppingCartIdResponse[]> {
    return this.http.get<ShoppingCartIdResponse[]>(`${this.url}/api/cart/public/carts/${id}`);
  }
  getShoppingCartByEmail(email: String): Observable<any> {
    return this.http.get<any>(`${this.url}/api/cart/public/carts/email/${email}`);
    //https://c15-21-m-java-angular-production.up.railway.app/api/cart/public/carts/email/david1@david.com
  }
  getShoppingCarts(): Observable<ShoppingCartResponse[]> {
    return this.http.get<ShoppingCartResponse[]>(`${this.url}/api/cart/admin/carts`);
  }
  deleteProductShoppingCart(cartId: Number,productId: Number): Observable<any> {
    return this.http.delete<any>(`${this.url}/api/cart/public/carts/${cartId}/product/${productId}`);
  }
}
