import { Component, inject } from '@angular/core';
import { ProductsResponse } from 'src/app/interfaces/products.interface';
import { Products } from 'src/app/interfaces/shoping-cart.interface';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent {

  ngOnInit(): void {
   
    //this.crearShoppingCart("david@david.com"); //Funcionando
    this.obtenerShoppingCartId(6); //funcionando
    //this.agregarAlShoppingCart(6,5,1); //funcionando
    //this.obtenerShoppingCarts()
    

  }

  private tiendaService = inject(EcoTiendaService);
  public productList: ProductsResponse[] = [];
  public shoppingCart: any= [];
  public productsShoppingCart: Products[] = [];
  obtenerShoppingCartId(id: number){
    this.tiendaService.getShoppingCartId(id).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart;
        this.productsShoppingCart=this.shoppingCart.products;
        console.log(this.shoppingCart)
        console.log(this.productsShoppingCart)
        
      }
    })
  }
}
