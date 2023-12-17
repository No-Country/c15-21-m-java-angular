import { Component, OnInit, inject } from '@angular/core';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';
import { ProductsResponse } from '../../../interfaces/products.interface';
import { CategoriesResponse } from 'src/app/interfaces/categories.interface';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css'],
})
export class HomePageComponent implements OnInit {
  private tiendaService = inject(EcoTiendaService);

  public productList: ProductsResponse[] = [];
  public categoriesList: CategoriesResponse[] = [];

  ngOnInit(): void {
    this.obtenerProductsList();
    this.obtenerCategoriesList();
  }

  obtenerProductsList() {
    this.tiendaService.getProducts().subscribe({
      next: (products) => {
        this.productList = products;
      },
    });
  }

  obtenerCategoriesList() {
    this.tiendaService.getCategories().subscribe({
      next: (categorias) => {
        this.categoriesList = categorias;
      },
    });
    this.tiendaService.getCategories().subscribe({
      next: (categories) => {
        this.categoriesList = categories;
        console.log(categories);
      },
    });
  }

  showModal(id: Number) {
    Swal.fire({
      title: `Agregar
      <input id="cantidad" type="number" value="1" style="text-align:center">
      Productos`,
      icon: "info",
      showCloseButton: true,
      showCancelButton: true,
      focusConfirm: false,
      confirmButtonText: `
      <i class="fa-solid fa-cart-plus"></i> Añadir al carrito
      `,
      confirmButtonAriaLabel: "Thumbs up, great!",
      cancelButtonText: `
        <i class="fa-solid fa-x"></i> Cancelar
      `,
      cancelButtonAriaLabel: "Thumbs down"
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        let cantidad = document.getElementById("cantidad") as HTMLInputElement;
        let cantidadValue = cantidad.value;
        console.log("agregar " + cantidadValue + " productos id: " + id)

        Swal.fire("Producto agregado!", "", "success");
      }
    });
  }
}