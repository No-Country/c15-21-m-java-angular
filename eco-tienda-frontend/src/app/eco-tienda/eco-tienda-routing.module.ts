import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { LayoutPageComponent } from './pages/layout-page/layout-page.component';

import { DetailPageComponent } from './pages/detail-page/detail-page.component';
import { ListPageComponent } from './pages/list-page/list-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { SearchResultsComponent } from './pages/search-results/search-results.component';
import { CategoryPageComponent } from './pages/category-page/category-page.component';
import { FeaturedPageComponent } from './pages/featured-page/featured-page.component';
import { TermsConditionsPageComponent } from './pages/terms-conditions/terms-conditions-page.component';
import { PrivacyPoliticsPageComponent } from './pages/privacy-politics/privacy-politics-page.component';
import { ReturnsRefundsPageComponent } from './pages/returns-refunds/returns-refunds-page.component';
import { ShippingDispatchPageComponent } from './pages/shippingdispatch/shipping-dispatch-page.component';
import { AboutPageComponent } from './pages/about/about-page.component';

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
      { path: 'conocenos', component: AboutPageComponent },
      { path: 'terminos-y-condiciones', component: TermsConditionsPageComponent },
      { path: 'politicas-de-privacidad', component:  PrivacyPoliticsPageComponent},
      { path: 'devoluciones-y-reembolsos', component:  ReturnsRefundsPageComponent},
      { path: 'envíos-y-despacho', component:  ShippingDispatchPageComponent},
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
