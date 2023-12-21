import { Component, OnInit, inject } from '@angular/core';
import { EcoTiendaService } from '../../../services/eco-tienda.service';
import { ProductsResponse } from '../../../interfaces/products.interface';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list-page',
  templateUrl: './list-page.component.html',
  styleUrls: ['./list-page.component.css'],
})
export class ListPageComponent implements OnInit {
  private tiendaService = inject(EcoTiendaService);
  public productList: ProductsResponse[] = [];

  public isLoading: boolean = false;

  ngOnInit(): void {
    this.obtenerProductsList();
  }

  obtenerProductsList() {
    this.isLoading = true;
    this.tiendaService.getProducts().subscribe({
      next: (products) => {
        this.productList = products;
        this.isLoading = false;
      },
    });
  }

  public shoppingCart: any = [];
  agregarAlShoppingCart(cart: Number, product: Number, quantity: Number) {
    this.tiendaService.addToShoppingCart(cart, product, quantity).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart;
        console.log(this.shoppingCart);
        Swal.fire("¡Producto agregado!", "", "success");
      }, error: (error) => {
        console.log(error);
        Swal.fire("¡No se pudo agregar el producto!", "", "error");
      }
    })
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
        let cantidadValue = parseInt(cantidad.value);
        this.agregarAlShoppingCart(Number(localStorage.getItem('cartId')), id, cantidadValue);
      }
    });
  }
}
