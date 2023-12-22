import { Component, OnInit } from '@angular/core';
import { SearchService } from 'src/app/services/search.service';
import { ResultsResponse } from 'src/app/interfaces/results.interface';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
})
export class SearchResultsComponent implements OnInit {
  searchResults$: Observable<ResultsResponse[]>= this.searchService.searchResults$; 
  results: ResultsResponse[] = [];
  public isLoading: boolean = false;

  constructor(
    private searchService: SearchService,
  ) {
  }

  ngOnInit(): void {
    this.searchService.searchResults$.subscribe((results) => {
      console.log('Resultados de la búsqueda:', results);
      this.results = results; 
    });
}
}