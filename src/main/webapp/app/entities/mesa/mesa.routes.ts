import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import MesaResolve from './route/mesa-routing-resolve.service';

const mesaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/mesa.component').then(m => m.MesaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/mesa-detail.component').then(m => m.MesaDetailComponent),
    resolve: {
      mesa: MesaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/mesa-update.component').then(m => m.MesaUpdateComponent),
    resolve: {
      mesa: MesaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/mesa-update.component').then(m => m.MesaUpdateComponent),
    resolve: {
      mesa: MesaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default mesaRoute;
