import { IPedidosProveedores, NewPedidosProveedores } from './pedidos-proveedores.model';

export const sampleWithRequiredData: IPedidosProveedores = {
  id: 21512,
};

export const sampleWithPartialData: IPedidosProveedores = {
  id: 28920,
  cantidad: 13392,
};

export const sampleWithFullData: IPedidosProveedores = {
  id: 19888,
  producto: 'fuzzy',
  cantidad: 831,
  valor: 26036,
};

export const sampleWithNewData: NewPedidosProveedores = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
