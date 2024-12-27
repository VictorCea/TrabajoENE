import { IIngresosDiarios, NewIngresosDiarios } from './ingresos-diarios.model';

export const sampleWithRequiredData: IIngresosDiarios = {
  id: 26489,
};

export const sampleWithPartialData: IIngresosDiarios = {
  id: 11760,
};

export const sampleWithFullData: IIngresosDiarios = {
  id: 29248,
  ventaNumero: 26523,
  valorVenta: 1925,
};

export const sampleWithNewData: NewIngresosDiarios = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
