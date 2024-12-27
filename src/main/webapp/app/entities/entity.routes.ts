import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'eneApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'usuario',
    data: { pageTitle: 'eneApp.usuario.home.title' },
    loadChildren: () => import('./usuario/usuario.routes'),
  },
  {
    path: 'producto',
    data: { pageTitle: 'eneApp.producto.home.title' },
    loadChildren: () => import('./producto/producto.routes'),
  },
  {
    path: 'mesa',
    data: { pageTitle: 'eneApp.mesa.home.title' },
    loadChildren: () => import('./mesa/mesa.routes'),
  },
  {
    path: 'cliente',
    data: { pageTitle: 'eneApp.cliente.home.title' },
    loadChildren: () => import('./cliente/cliente.routes'),
  },
  {
    path: 'proveedor',
    data: { pageTitle: 'eneApp.proveedor.home.title' },
    loadChildren: () => import('./proveedor/proveedor.routes'),
  },
  {
    path: 'pedidos-proveedores',
    data: { pageTitle: 'eneApp.pedidosProveedores.home.title' },
    loadChildren: () => import('./pedidos-proveedores/pedidos-proveedores.routes'),
  },
  {
    path: 'recetas',
    data: { pageTitle: 'eneApp.recetas.home.title' },
    loadChildren: () => import('./recetas/recetas.routes'),
  },
  {
    path: 'ingresos-diarios',
    data: { pageTitle: 'eneApp.ingresosDiarios.home.title' },
    loadChildren: () => import('./ingresos-diarios/ingresos-diarios.routes'),
  },
  {
    path: 'egresos-diarios',
    data: { pageTitle: 'eneApp.egresosDiarios.home.title' },
    loadChildren: () => import('./egresos-diarios/egresos-diarios.routes'),
  },
  {
    path: 'ordenes-cocina',
    data: { pageTitle: 'eneApp.ordenesCocina.home.title' },
    loadChildren: () => import('./ordenes-cocina/ordenes-cocina.routes'),
  },
  {
    path: 'menu',
    data: { pageTitle: 'eneApp.menu.home.title' },
    loadChildren: () => import('./menu/menu.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
