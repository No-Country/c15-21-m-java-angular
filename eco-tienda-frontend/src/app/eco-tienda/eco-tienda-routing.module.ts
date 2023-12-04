import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutPageComponent } from './pages/layout-page/layout-page.component';

import { DetailPageComponent } from './pages/detail-page/detail-page.component';
import { ListPageComponent } from './pages/list-page/list-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { SigninPageComponent } from './pages/signin-page/signin-page.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutPageComponent,
    children: [
      { path: 'home', component: HomePageComponent },
      { path: 'detail', component: DetailPageComponent },
      { path: 'list', component: ListPageComponent },
      { path: 'signin', component: SigninPageComponent},
      { path: '**', redirectTo: 'home' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EcoTiendaRoutingModule {}
