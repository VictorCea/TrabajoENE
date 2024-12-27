import { IProducto, NewProducto } from './producto.model';

export const sampleWithRequiredData: IProducto = {
  id: 26142,
};

export const sampleWithPartialData: IProducto = {
  id: 9891,
  stock: 25273.56,
};

export const sampleWithFullData: IProducto = {
  id: 31795,
  nombre: 'er behold needily',
  stock: 12411.58,
};

export const sampleWithNewData: NewProducto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
