import { Component, inject } from '@angular/core';
import { ShoppingCartIdResponse } from 'src/app/interfaces/shoping-cart.interface';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {

  private tiendaService = inject(EcoTiendaService);
  public shoppingCart: ShoppingCartIdResponse[] = [];

  
  /* al crear usuario se le debe crear un carrito

  crearShoppingCart("Ingrese@su.email");
  */

  crearShoppingCart(email: String){
    this.tiendaService.createShoppingCart(email).subscribe({
      next: (shoppingCart) => {
      
        this.shoppingCart = shoppingCart
        console.log(this.shoppingCart)
      }
    })
  }
  
  
  
}
