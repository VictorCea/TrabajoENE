import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import EgresosDiariosResolve from './route/egresos-diarios-routing-resolve.service';

const egresosDiariosRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/egresos-diarios.component').then(m => m.EgresosDiariosComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/egresos-diarios-detail.component').then(m => m.EgresosDiariosDetailComponent),
    resolve: {
      egresosDiarios: EgresosDiariosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/egresos-diarios-update.component').then(m => m.EgresosDiariosUpdateComponent),
    resolve: {
      egresosDiarios: EgresosDiariosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/egresos-diarios-update.component').then(m => m.EgresosDiariosUpdateComponent),
    resolve: {
      egresosDiarios: EgresosDiariosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default egresosDiariosRoute;
