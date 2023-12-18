import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EcoTiendaService } from '../../../services/eco-tienda.service';
import { switchMap } from 'rxjs';
import { DetailResponse } from '../../../interfaces/detail.interface';

@Component({
  selector: 'app-detail-page',
  templateUrl: './detail-page.component.html',
  styleUrls: ['./detail-page.component.css'],
})
export class DetailPageComponent implements OnInit {
  private activatedRoute = inject(ActivatedRoute);

  private ecoTiendaService = inject(EcoTiendaService);

  private router = inject(Router);

  public product?: DetailResponse;

  public isLoading: boolean = false;

  ngOnInit(): void {
    this.getProductDetail();
  }

  getProductDetail() {
    this.isLoading = true;
    this.activatedRoute.params
      .pipe(
        switchMap(({ id }) => this.ecoTiendaService.searchProductDetail(id))
      )
      .subscribe({
        next: (product) => {
          this.product = product;
          this.isLoading = false;
          console.log(product);
        },
        error: () => {
          this.router.navigateByUrl('/404');
        },
      });
  }
}
