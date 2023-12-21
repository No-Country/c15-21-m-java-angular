import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Error404PageComponent } from './pages/error404-page/error404-page.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { SearchResultsComponent } from '../eco-tienda/pages/search-results/search-results.component';
import { SearchBoxComponent } from './components/search-box/search-box.component';
import { RouterModule } from '@angular/router';
import { LoaderComponent } from './components/loader/loader.component';

@NgModule({
  declarations: [
    Error404PageComponent,
    HeaderComponent,
    FooterComponent,
    SearchBoxComponent,
    LoaderComponent,
    SearchResultsComponent,
  ],
  imports: [CommonModule, RouterModule],
  exports: [
    HeaderComponent,
    FooterComponent,
    SearchBoxComponent,
    LoaderComponent,
    SearchResultsComponent,
  ],
})
export class SharedModule {}
