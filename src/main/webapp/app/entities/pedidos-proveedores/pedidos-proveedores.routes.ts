import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PedidosProveedoresResolve from './route/pedidos-proveedores-routing-resolve.service';

const pedidosProveedoresRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/pedidos-proveedores.component').then(m => m.PedidosProveedoresComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/pedidos-proveedores-detail.component').then(m => m.PedidosProveedoresDetailComponent),
    resolve: {
      pedidosProveedores: PedidosProveedoresResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/pedidos-proveedores-update.component').then(m => m.PedidosProveedoresUpdateComponent),
    resolve: {
      pedidosProveedores: PedidosProveedoresResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/pedidos-proveedores-update.component').then(m => m.PedidosProveedoresUpdateComponent),
    resolve: {
      pedidosProveedores: PedidosProveedoresResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pedidosProveedoresRoute;
