import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EcoTiendaRoutingModule } from './eco-tienda-routing.module';
import { LayoutPageComponent } from './pages/layout-page/layout-page.component';
import { ListPageComponent } from './pages/list-page/list-page.component';
import { DetailPageComponent } from './pages/detail-page/detail-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    LayoutPageComponent,
    ListPageComponent,
    DetailPageComponent,
    HomePageComponent,
  ],
  imports: [CommonModule, EcoTiendaRoutingModule, SharedModule],
})
export class EcoTiendaModule {}
