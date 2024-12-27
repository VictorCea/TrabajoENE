import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import RecetasResolve from './route/recetas-routing-resolve.service';

const recetasRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/recetas.component').then(m => m.RecetasComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/recetas-detail.component').then(m => m.RecetasDetailComponent),
    resolve: {
      recetas: RecetasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/recetas-update.component').then(m => m.RecetasUpdateComponent),
    resolve: {
      recetas: RecetasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/recetas-update.component').then(m => m.RecetasUpdateComponent),
    resolve: {
      recetas: RecetasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default recetasRoute;
