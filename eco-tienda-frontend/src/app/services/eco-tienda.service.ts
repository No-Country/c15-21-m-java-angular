import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environments } from '../../environments/environments';
import { Observable } from 'rxjs';
import { ProductsResponse } from '../interfaces/products.interface';
import { CategoriesResponse } from '../interfaces/products.interface';

@Injectable({
  providedIn: 'root',
})
export class EcoTiendaService {
  private url: string = environments.baseUrl;

  private http = inject(HttpClient);


  getCategories(): Observable<CategoriesResponse[]> {
    return this.http.get<CategoriesResponse[]>(
      `${this.url}/v1/categorias`
    );
  }

  getProducts(): Observable<ProductsResponse[]> {
    return this.http.get<ProductsResponse[]>(
      `${this.url}/administrador/productos`
    );
  }


}
