import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Error404PageComponent } from './shared/pages/error404-page/error404-page.component';
import { SearchResultsComponent } from './eco-tienda/pages/search-results/search-results.component';
import { isAuthenticatedGuard } from './guards/is-authenticated.guard';
import { alreadyLoggedGuard } from './guards/already-logged.guard';

const routes: Routes = [
  {
    path: 'auth',
    canActivate: [alreadyLoggedGuard],
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'eco-tienda',
    loadChildren: () =>
      import('./eco-tienda/eco-tienda.module').then((m) => m.EcoTiendaModule),
  },
  {
    path: 'shopping-cart',
    canActivate: [isAuthenticatedGuard],
    loadChildren: () =>
      import('./shopping-cart/shopping-cart.module').then(
        (m) => m.ShoppingCartModule
      ),
  },
  {
    path: '404',
    component: Error404PageComponent,
  },
  {
    path: '',
    redirectTo: 'eco-tienda',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: '404',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
