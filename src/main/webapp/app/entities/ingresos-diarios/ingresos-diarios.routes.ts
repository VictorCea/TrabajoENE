import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import IngresosDiariosResolve from './route/ingresos-diarios-routing-resolve.service';

const ingresosDiariosRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/ingresos-diarios.component').then(m => m.IngresosDiariosComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/ingresos-diarios-detail.component').then(m => m.IngresosDiariosDetailComponent),
    resolve: {
      ingresosDiarios: IngresosDiariosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/ingresos-diarios-update.component').then(m => m.IngresosDiariosUpdateComponent),
    resolve: {
      ingresosDiarios: IngresosDiariosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/ingresos-diarios-update.component').then(m => m.IngresosDiariosUpdateComponent),
    resolve: {
      ingresosDiarios: IngresosDiariosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ingresosDiariosRoute;
