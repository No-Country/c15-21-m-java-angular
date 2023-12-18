import { Component, OnInit, inject } from '@angular/core';
import { EcoTiendaService } from '../../../services/eco-tienda.service';
import { ProductsResponse } from '../../../interfaces/products.interface';

@Component({
  selector: 'app-featured-page',
  templateUrl: './featured-page.component.html',
  styleUrls: ['./featured-page.component.css'],
})
export class FeaturedPageComponent implements OnInit {
  private tiendaService = inject(EcoTiendaService);
  public productList: ProductsResponse[] = [];

  public isLoading: boolean = false;

  ngOnInit(): void {
    this.obtenerProductsList();
  }

  obtenerProductsList() {
    this.isLoading = true;
    this.tiendaService.getProducts().subscribe({
      next: (products) => {
        this.productList = products;
        this.isLoading = false;
      },
    });
  }
}
