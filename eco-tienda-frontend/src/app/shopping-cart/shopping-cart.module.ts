import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ShoppingCartRoutingModule } from './shopping-cart-routing.module';
import { LayoutPageComponent } from './pages/layout-page/layout-page.component';
import { CartPageComponent } from './pages/cart-page/cart-page.component';
import { SharedModule } from "../shared/shared.module";
import { CheckoutComponent } from './pages/checkout-page/checkout/checkout.component';


@NgModule({
    declarations: [
        LayoutPageComponent,
        CartPageComponent,
        CheckoutComponent
    ],
    imports: [
        CommonModule,
        ShoppingCartRoutingModule,
        SharedModule
    ]
})
export class ShoppingCartModule { }
