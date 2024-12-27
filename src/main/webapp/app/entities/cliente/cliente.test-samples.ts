import { ICliente, NewCliente } from './cliente.model';

export const sampleWithRequiredData: ICliente = {
  id: 2897,
};

export const sampleWithPartialData: ICliente = {
  id: 28503,
  orden: 'boo bob',
  cantidadClientes: 11325,
};

export const sampleWithFullData: ICliente = {
  id: 17129,
  mesa: 1359,
  orden: 'nor',
  cantidadClientes: 19260,
};

export const sampleWithNewData: NewCliente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
