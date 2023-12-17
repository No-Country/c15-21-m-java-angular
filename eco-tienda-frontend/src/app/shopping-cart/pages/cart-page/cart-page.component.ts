import { Component, inject } from '@angular/core';
import { ProductsResponse } from 'src/app/interfaces/products.interface';
import { ShoppingCartResponse } from 'src/app/interfaces/shoping-cart.interface';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.css']
})
export class CartPageComponent {

  private tiendaService = inject(EcoTiendaService);
  public productList: ProductsResponse[] = [];
  public shoppingCart: ShoppingCartResponse[] = [];

  ngOnInit(): void {
    this.obtenerShoppingCart();
    this.obtenerProductsList();
  }

  obtenerProductsList(){
    this.tiendaService.getProducts().subscribe({
      next: (products) => {
        this.productList =products;
        console.log(this.productList)
      }
    })
  }

  obtenerShoppingCart(){
    this.tiendaService.getShoppingCart().subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart
      }
    })
  }

  showModal(id: Number) {
  }
}
