import { Component, OnInit, inject } from '@angular/core';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';
import { ProductsResponse } from '../../../interfaces/products.interface';
import { CategoriesResponse } from 'src/app/interfaces/categories.interface';

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

  ngOnInit(): void {
    this.obtenerProductsList();
    this.obtenerCategoriesList();
  }

  obtenerProductsList() {
    this.isLoadingProducts = true;
    this.tiendaService.getProducts().subscribe({
      next: (products) => {
        this.productList = products;
        this.isLoadingProducts = false;
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
  }
}
