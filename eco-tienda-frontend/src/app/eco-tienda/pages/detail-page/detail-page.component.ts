import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EcoTiendaService } from '../../../services/eco-tienda.service';
import { switchMap } from 'rxjs';
import { DetailResponse } from '../../../interfaces/detail.interface';
import Swal from 'sweetalert2';
import { Products } from 'src/app/interfaces/shoping-cart.interface';

@Component({
  selector: 'app-detail-page',
  templateUrl: './detail-page.component.html',
  styleUrls: ['./detail-page.component.css'],
})
export class DetailPageComponent implements OnInit {
  private activatedRoute = inject(ActivatedRoute);

  private ecoTiendaService = inject(EcoTiendaService);

  private router = inject(Router);

  public product?: DetailResponse;

  public isLoading: boolean = false;

  
  
  public productsShoppingCart: Products[] = [];
  public shoppingCart: any = { products: [] };
  public ShoppingCartId = Number(localStorage.getItem('cartId'));
  public isLoadingCart: boolean = false;
  
  private tiendaService = inject(EcoTiendaService);
  ngOnInit(): void {
    this.getProductDetail();
    
    this.obtenerShoppingCartId(this.ShoppingCartId)
  }

  getProductDetail() {
    this.isLoading = true;
    this.activatedRoute.params
      .pipe(
        switchMap(({ id }) => this.ecoTiendaService.searchProductDetail(id))
      )
      .subscribe({
        next: (product) => {
          this.product = product;
          this.isLoading = false;
          console.log(product);
        },
        error: () => {
          this.router.navigateByUrl('/404');
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
}
