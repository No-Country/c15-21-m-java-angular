import { Component, inject } from '@angular/core';
import { ProductsResponse } from 'src/app/interfaces/products.interface';
import { Products } from 'src/app/interfaces/shoping-cart.interface';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';
import { loadMercadoPago } from "@mercadopago/sdk-js";

 

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
  public isLoadingCart: boolean = false;

  obtenerShoppingCartId(id: number){
    this.isLoadingCart = true;

    this.tiendaService.getShoppingCartId(id).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart;
        this.productsShoppingCart=this.shoppingCart.products;
        this.isLoadingCart = false;

        
      }
    })
  }

  /****************************** Mercado Pago *******************************/
/* 
 const mp = new MercadoPago('APP_USR-132d9d07-1a00-419f-95da-5d6473e181c8'); */


 /* const mp = new MercadoPago('***************');
 const bricksBuilder = mp.bricks(); */


}
