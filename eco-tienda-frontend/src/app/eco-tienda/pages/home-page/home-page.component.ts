import { Component, OnInit, inject } from '@angular/core';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';
import { ProductsResponse } from '../../../interfaces/products.interface';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css'],
})
export class HomePageComponent implements OnInit {
  public productList: ProductsResponse[] = [];

  private tiendaService = inject(EcoTiendaService);

  public listProducts = [
    { title: 'Jabones Naturales', path: '../../../../assets/et_jabones.png' },
    { title: 'Higiene Dental', path: '../../../../assets/et_dental.png' },
    { title: 'Champú Solido', path: '../../../../assets/et_shampu.png' },
    {
      title: 'Desodorantes Sólidos',
      path: '../../../../assets/et_desodorantes.png',
    },
    { title: 'Cuidado de la Piel', path: '../../../../assets/et_cremas.png' },
    { title: 'Cosmética Facial', path: '../../../../assets/et_cosmetica.png' },
  ];

  ngOnInit(): void {
    this.tiendaService.getProducts().subscribe({
      next: (products) => {
        this.productList = products;
        console.log(products);
      },
    });
  }
}
