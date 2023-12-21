import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environments } from '../../environments/environments';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { SearchService } from './search.service';
import { ProductsResponse } from '../interfaces/products.interface';
import { CategoriesResponse } from '../interfaces/categories.interface';
import { DetailResponse } from '../interfaces/detail.interface';
import { ResultsResponse } from '../interfaces/results.interface';

@Injectable({
  providedIn: 'root',
})
export class EcoTiendaService {
  private url: string = environments.baseUrl;

  // Inyecta HttpClient directamente en el constructor
  constructor(private http: HttpClient, private searchService: SearchService) {}

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

  searchProductDetail(id: string): Observable<DetailResponse> {
    return this.http.get<DetailResponse>(
      `${this.url}/administrador/productos/${id}`
    );
  }

  searchProductByBox(keyword: string): Observable<ResultsResponse[]> {
    const url = `${this.url}/buscar/productos?palabraClave=${keyword}`;
    return this.http.get<ResultsResponse[]>(url).pipe(
      tap((results) => this.searchService.updateSearchResults(results))
    );
  }
}
