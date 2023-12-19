import { Component, inject } from '@angular/core';
import {Location} from '@angular/common';
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

  
  constructor(private _location: Location) 
  {}
  
  ngOnInit(): void {
    this.obtenerProductsList();
    this.obtenerShoppingCart();
   /*  this.crearShoppingCart();
    this.agregarAlShoppingCart(); */
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
        console.log(this.shoppingCart)
      }
    })
  }
  crearShoppingCart(){
    this.tiendaService.createShoppingCart("alexiz@gmail.com").subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart
        console.log(this.shoppingCart)
      }
    })
  }
  agregarAlShoppingCart(){
    this.tiendaService.addToShoppingCart(1,5,3).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart
        console.log(this.shoppingCart)
      }
    })
  }

  showModal(id: Number) {
  }


  backClicked() {
    this._location.back();
  }
}
