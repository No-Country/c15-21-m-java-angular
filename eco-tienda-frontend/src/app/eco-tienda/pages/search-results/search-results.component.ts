import { Component, OnInit, inject } from '@angular/core';
import { SearchService } from 'src/app/services/search.service';
import { ResultsResponse } from 'src/app/interfaces/results.interface';
import { Observable } from 'rxjs';

import Swal from 'sweetalert2';
import { EcoTiendaService } from '../../../services/eco-tienda.service';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
})
export class SearchResultsComponent implements OnInit {
  searchResults$: Observable<ResultsResponse[]> =
    this.searchService.searchResults$;
  results: ResultsResponse[] = [];
  public isLoadingResults: boolean = false;

  public shoppingCart: any = [];

  private tiendaService = inject(EcoTiendaService);

  constructor(private searchService: SearchService) { }

  ngOnInit(): void {
    this.isLoadingResults = true;
    this.searchService.searchResults$.subscribe((results) => {
      console.log('Resultados de la búsqueda:', results);
      this.results = results;
      this.isLoadingResults = false;
    });
  }

  agregarAlShoppingCart(cart: Number, product: Number, quantity: Number) {
    this.tiendaService.addToShoppingCart(cart, product, quantity).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart;
        console.log(this.shoppingCart);
        Swal.fire('¡Producto agregado!', '', 'success');
      },
      error: (error) => {
        console.log(error);
        Swal.fire('¡No se pudo agregar el producto!', '', 'error');
      },
    });
  }

  showModal(id: Number) {
    Swal.fire({
      title: `Agregar
      <input id="cantidad" type="number" value="1" style="text-align:center">
      Productos`,
      icon: 'info',
      showCloseButton: true,
      showCancelButton: true,
      focusConfirm: false,
      confirmButtonText: `
      <i class="fa-solid fa-cart-plus"></i> Añadir al carrito
      `,
      confirmButtonAriaLabel: 'Thumbs up, great!',
      cancelButtonText: `
        <i class="fa-solid fa-x"></i> Cancelar
      `,
      cancelButtonAriaLabel: 'Thumbs down',
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        let cantidad = document.getElementById('cantidad') as HTMLInputElement;
        let cantidadValue = parseInt(cantidad.value);
        this.agregarAlShoppingCart(
          Number(localStorage.getItem('cartId')),
          id,
          cantidadValue
        );
      }
    });
  }
}
