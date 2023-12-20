import { Component, inject } from '@angular/core';
import {Location} from '@angular/common';
import { ProductsResponse } from 'src/app/interfaces/products.interface';
import { Products, ShoppingCartIdResponse } from 'src/app/interfaces/shoping-cart.interface';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.css']
})
export class CartPageComponent {

  private tiendaService = inject(EcoTiendaService);
  public productList: ProductsResponse[] = [];
  public shoppingCart: any= [];
  public productsShoppingCart: Products[] = [];

  
  constructor(private _location: Location) 
  {}
  
  ngOnInit(): void {
    this.obtenerProductsList();
   
    //this.crearShoppingCart("david@david.com"); //Funcionando
    this.obtenerShoppingCartId(6); //funcionando
    //this.agregarAlShoppingCart(6,5,1); //funcionando
    //this.obtenerShoppingCarts()
    

  }

precioxcantidad(precio: number, cantidad: number){
  return precio * cantidad;
}

  obtenerProductsList(){
    this.tiendaService.getProducts().subscribe({
      next: (products) => {
        this.productList =products;
        console.log(this.productList)
      }
    })
  }
  
  obtenerShoppingCartId(id: Number){
    this.tiendaService.getShoppingCartId(id).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart;
        this.productsShoppingCart=this.shoppingCart.products;
        console.log(this.shoppingCart)
        console.log(this.productsShoppingCart)
        
      }
    })
  }
  
  crearShoppingCart(email: String){
    this.tiendaService.createShoppingCart(email).subscribe({
      next: (shoppingCart) => {
      
        this.shoppingCart = shoppingCart
        console.log(this.shoppingCart)
      }
    })
  }
  
  
  agregarAlShoppingCart(cart: Number,product: Number,quantity: Number){
    this.tiendaService.addToShoppingCart(cart,product,quantity).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart;
        console.log(this.shoppingCart);
        Swal.fire("¡Producto agregado!", "", "success");          
      },error: (error) => {
        console.log(error);
        Swal.fire("¡No se pudo agregar el producto!", "", "error");
      }
    })
  }
  
  obtenerShoppingCarts(){
        this.tiendaService.getShoppingCarts().subscribe({
          next: (shoppingCart) => {
            this.shoppingCart = shoppingCart
            console.log(this.shoppingCart)
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
        this.agregarAlShoppingCart(6, id, cantidadValue);
          
        
      }
    });
  }


  backClicked() {
    this._location.back();
  }
}
