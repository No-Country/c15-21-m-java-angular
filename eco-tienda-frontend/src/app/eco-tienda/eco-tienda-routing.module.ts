import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { LayoutPageComponent } from './pages/layout-page/layout-page.component';

import { DetailPageComponent } from './pages/detail-page/detail-page.component';
import { ListPageComponent } from './pages/list-page/list-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';

import { CategoryPageComponent } from './pages/category-page/category-page.component';
import { FeaturedPageComponent } from './pages/featured-page/featured-page.component';
import { SearchResultsComponent } from './pages/search-results/search-results.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutPageComponent,
    children: [
      { path: 'home', component: HomePageComponent },
      { path: 'detail/:id', component: DetailPageComponent },
      { path: 'list', component: ListPageComponent },
      { path: 'category', component: CategoryPageComponent },
      { path: 'destacado', component: FeaturedPageComponent },
      { path: 'search-results', component: SearchResultsComponent },
      { path: '**', redirectTo: 'home' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes), ReactiveFormsModule],
  exports: [RouterModule],
})
export class EcoTiendaRoutingModule {}
