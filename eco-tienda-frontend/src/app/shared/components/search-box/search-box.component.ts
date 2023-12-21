import { Component, ElementRef, ViewChild, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { environments } from 'src/environments/environments';
import { SearchService } from 'src/app/services/search.service';
import { EcoTiendaService } from 'src/app/services/eco-tienda.service';

@Component({
  selector: 'shared-search-box',
  templateUrl: './search-box.component.html',
  styleUrls: ['./search-box.component.css'],
})
export class SearchBoxComponent {
  @ViewChild('txtTagInput') tagInput?: ElementRef<HTMLInputElement>;
  private url: string = environments.baseUrl;
  results : any;

  constructor(
    private ecoTiendaService: EcoTiendaService,
    private searchService: SearchService,
    private router: Router
  ) {}

  searchTag() {
    const newTag = this.tagInput?.nativeElement.value;

    if (newTag) {
      // Utiliza directamente la URL base desde el servicio EcoTiendaService
      const url = `${this.url}/buscar/productos?palabraClave=${newTag}`;
      console.log('URL de búsqueda:', url);

      this.ecoTiendaService.searchProductByBox(newTag).subscribe(
        (response) => {
          console.log('Resultados de la búsqueda:', response);
          this.renderResults(response); // Llama a renderResults con los resultados
        },
        (error) => {
          console.error('Error al realizar la búsqueda:', error);
        }
      );
    }
    
    
  }

  private renderResults(results: any): void {
    this.results = results;
    this.router.navigateByUrl('eco-tienda/search-results');
    this.searchService.updateSearchResults(results);
  }
}

