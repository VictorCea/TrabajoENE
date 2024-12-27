import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import OrdenesCocinaResolve from './route/ordenes-cocina-routing-resolve.service';

const ordenesCocinaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/ordenes-cocina.component').then(m => m.OrdenesCocinaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/ordenes-cocina-detail.component').then(m => m.OrdenesCocinaDetailComponent),
    resolve: {
      ordenesCocina: OrdenesCocinaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/ordenes-cocina-update.component').then(m => m.OrdenesCocinaUpdateComponent),
    resolve: {
      ordenesCocina: OrdenesCocinaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/ordenes-cocina-update.component').then(m => m.OrdenesCocinaUpdateComponent),
    resolve: {
      ordenesCocina: OrdenesCocinaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ordenesCocinaRoute;
