import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CategoriesResponse } from 'src/app/interfaces/categories.interface';

@Injectable({
  providedIn: 'root'
})
export class CategorySharedService {
  private categoriaSeleccionadaSubject = new BehaviorSubject<CategoriesResponse | null>(null);
  categoriaSeleccionada$: Observable<CategoriesResponse | null> = this.categoriaSeleccionadaSubject.asObservable();

  actualizarCategoriaSeleccionada(categoria: CategoriesResponse | null) {
    this.categoriaSeleccionadaSubject.next(categoria);
  }
}