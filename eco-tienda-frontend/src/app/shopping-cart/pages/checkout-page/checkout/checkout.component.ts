import { Component, inject } from '@angular/core';
import { ProductsResponse } from 'src/app/interfaces/products.interface';
import { Products } from 'src/app/interfaces/shoping-cart.interface';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent {
  private tiendaService = inject(EcoTiendaService);
  public productList: ProductsResponse[] = [];
  public shoppingCart: any = [];
  public productsShoppingCart: Products[] = [];

  public ShoppingCartId = Number(localStorage.getItem('cartId'));
  public isLoadingCart: boolean = false;

  ngOnInit(): void {
    //this.crearShoppingCart("david@david.com"); //Funcionando
    //funcionando
    //this.agregarAlShoppingCart(6,5,1); //funcionando
    //this.obtenerShoppingCarts()

    if (this.ShoppingCartId > 0) {
      this.obtenerShoppingCartId(this.ShoppingCartId);
    }
  }
  obtenerShoppingCartId(id: number) {
    this.isLoadingCart = true;

    this.tiendaService.getShoppingCartId(id).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart;
        this.productsShoppingCart = this.shoppingCart.products;
        this.isLoadingCart = false;
      },
    });
  }
}
