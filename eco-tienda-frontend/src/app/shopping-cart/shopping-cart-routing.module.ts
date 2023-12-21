import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutPageComponent } from './pages/layout-page/layout-page.component';
import { CartPageComponent } from './pages/cart-page/cart-page.component';
import { CheckoutComponent } from './pages/checkout-page/checkout/checkout.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutPageComponent,
    children: [
      { path: 'cart', component: CartPageComponent },
      { path: 'checkout', component: CheckoutComponent },
      { path: '**', redirectTo: 'cart' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ShoppingCartRoutingModule {}
