import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { EcoTiendaRoutingModule } from './eco-tienda-routing.module';
import { LayoutPageComponent } from './pages/layout-page/layout-page.component';
import { ListPageComponent } from './pages/list-page/list-page.component';
import { DetailPageComponent } from './pages/detail-page/detail-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { SharedModule } from '../shared/shared.module';
import { FeaturedPageComponent } from './pages/featured-page/featured-page.component';
import { CategoryPageComponent } from './pages/category-page/category-page.component';
import { SearchResultsComponent } from './pages/search-results/search-results.component';

@NgModule({
  declarations: [
    LayoutPageComponent,
    ListPageComponent,
    DetailPageComponent,
    HomePageComponent,
    FeaturedPageComponent,
    CategoryPageComponent,
    SearchResultsComponent,
  ],
  imports: [CommonModule, EcoTiendaRoutingModule, SharedModule],
})
export class EcoTiendaModule {}
