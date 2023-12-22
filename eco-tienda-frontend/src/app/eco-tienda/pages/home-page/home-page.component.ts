import { Component, OnInit, inject } from '@angular/core';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';
import { ProductsResponse } from '../../../interfaces/products.interface';
import { CategoriesResponse } from 'src/app/interfaces/categories.interface';
import Swal from 'sweetalert2';
import { Products } from 'src/app/interfaces/shoping-cart.interface';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css'],
})
export class HomePageComponent implements OnInit {
  private tiendaService = inject(EcoTiendaService);

  public productList: ProductsResponse[] = [];
  public categoriesList: CategoriesResponse[] = [];

  public isLoadingCategories: boolean = false;
  public isLoadingProducts: boolean = false;



  public productsShoppingCart: Products[] = [];
  public shoppingCart: any = { products: [] };
  public ShoppingCartId = Number(localStorage.getItem('cartId'));
  public isLoadingCart: boolean = false;

  ngOnInit(): void {
    this.obtenerProductsList();
    this.obtenerCategoriesList();
    this.obtenerShoppingCartId(this.ShoppingCartId)
  }

  obtenerProductsList() {
    this.isLoadingProducts = true;
    this.tiendaService.getProducts().subscribe({
      next: (products) => {
        this.productList = products;
        this.isLoadingProducts = false;
        console.log(this.productList)
      },
    });
  }

  obtenerCategoriesList() {
    this.isLoadingCategories = true;
    this.tiendaService.getCategories().subscribe({
      next: (categorias) => {
        this.categoriesList = categorias;
        this.isLoadingCategories = false;
      },
    });
    this.tiendaService.getCategories().subscribe({
      next: (categories) => {
        this.categoriesList = categories;
        console.log(categories);
      },
    });
  }

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

  obtenerShoppingCartId(id: Number) {
    this.isLoadingCart = true;

    this.tiendaService.getShoppingCartId(id).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart;
        this.productsShoppingCart = this.shoppingCart.products;
        this.isLoadingCart = false;
        console.log(this.productsShoppingCart);

      }
    })
  }

  existe(id: Number) {
    for (let i = 0; i < this.productsShoppingCart.length; i++) {
      if (id === this.productsShoppingCart[i].id) {
        return true;
      }
    }
    return false;
  }

  showModal(id: Number, stock: Number) {
    Swal.fire({
      title: `Agregar
      <input id="cantidad" type="number" value="1" style="text-align:center" min="1" max="`+ stock + `">
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
  // Función para determinar si una categoría debe mostrarse
  debeMostrarse(categoria: CategoriesResponse): boolean {
    // Devuelve true solo si el ID de la categoría NO es igual a 7 
    return categoria.id !== 7;
  }
}