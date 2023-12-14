import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'shared-search-box',
  templateUrl: './search-box.component.html',
  styleUrls: ['./search-box.component.css'],
})
export class SearchBoxComponent {
  @ViewChild('txtTagInput')
  public tagInput?: ElementRef<HTMLInputElement>;

  private router = inject(Router);

  searchTag() {
    const newTag = this.tagInput?.nativeElement.value;
    console.log(newTag);
    this.router.navigateByUrl('/eco-tienda/list');
  }
}
