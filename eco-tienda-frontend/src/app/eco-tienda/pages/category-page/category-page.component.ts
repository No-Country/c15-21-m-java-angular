import { Component, OnInit } from '@angular/core';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';
import { CategorySharedService } from 'src/app/services/categoryShared.service';
import Swal from 'sweetalert2';
import { ProductsResponse } from '../../../interfaces/products.interface';
import { CategoriesResponse } from 'src/app/interfaces/categories.interface';

@Component({
  selector: 'app-category-page',
  templateUrl: './category-page.component.html',
  styleUrls: ['./category-page.component.css']
})
export class CategoryPageComponent implements OnInit {
  productos: ProductsResponse[] = [];
  categoriaSeleccionada: CategoriesResponse | null = null;
  productosFiltradosPorCategoria: ProductsResponse[] = []; // Agrega esta línea
  // public isLoading: boolean = false;
  public isLoadingProducts: boolean = false;
  public shoppingCart: any = [];
  
  constructor(private ecoTiendaService: EcoTiendaService, private categorysharedService: CategorySharedService, private tiendaService: EcoTiendaService) {}

  ngOnInit(): void {
    this.categorysharedService.categoriaSeleccionada$.subscribe((categoria) => {
      this.categoriaSeleccionada = categoria;
      this.renderizarProductosPorCategoria();
    });
  }

  private filtrarPorCategoria(categoria: CategoriesResponse): ProductsResponse[] {
    return this.productos.filter((producto) => producto.categorias.some(c => c.nombre === categoria.nombre));
  }

  private async renderizarProductosPorCategoria() {
    try {
      this.isLoadingProducts = true;
      // Obtener productos
      const response = await this.ecoTiendaService.getProducts().toPromise();
      if (response) {
        this.productos = response;

        // Filtrar productos por la categoría seleccionada
        this.productosFiltradosPorCategoria = this.categoriaSeleccionada ?
          this.filtrarPorCategoria(this.categoriaSeleccionada) :
          this.productos;

        // Puedes manipular productosFiltradosPorCategoria como desees
        console.log('Productos de la categoría seleccionada:', this.productosFiltradosPorCategoria);
      } else {
        console.error('La respuesta del servicio es indefinida.');
      }
    } catch (error) {
      console.error('Error al obtener productos:', error);
    } finally {
      this.isLoadingProducts = false;
    }
    
  }
  agregarAlShoppingCart(cart: Number, product: Number, quantity: Number) {
    this.tiendaService.addToShoppingCart(cart, product, quantity).subscribe({
      next: (shoppingCart) => {
        this.shoppingCart = shoppingCart;
        console.log(this.shoppingCart);
        Swal.fire('¡Producto agregado!', '', 'success');
      },
      error: (error) => {
        console.log(error);
        Swal.fire('¡No se pudo agregar el producto!', '', 'error');
      },
    });
  }
  showModal(id: Number) {
    Swal.fire({
      title: `Agregar
      <input id="cantidad" type="number" value="1" style="text-align:center">
      Productos`,
      icon: 'info',
      showCloseButton: true,
      showCancelButton: true,
      focusConfirm: false,
      confirmButtonText: `
      <i class="fa-solid fa-cart-plus"></i> Añadir al carrito
      `,
      confirmButtonAriaLabel: 'Thumbs up, great!',
      cancelButtonText: `
        <i class="fa-solid fa-x"></i> Cancelar
      `,
      cancelButtonAriaLabel: 'Thumbs down',
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        let cantidad = document.getElementById('cantidad') as HTMLInputElement;
        let cantidadValue = parseInt(cantidad.value);
        this.agregarAlShoppingCart(
          Number(localStorage.getItem('cartId')),
          id,
          cantidadValue
        );
      }
    });
  }
}


