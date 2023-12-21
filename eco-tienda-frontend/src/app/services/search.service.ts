import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ResultsResponse } from '../interfaces/results.interface';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  private searchResultsSubject = new BehaviorSubject<ResultsResponse[]>([]);
  searchResults$ = this.searchResultsSubject.asObservable();

  updateSearchResults(results: ResultsResponse[]): void {
    this.searchResultsSubject.next(results);
  }
}