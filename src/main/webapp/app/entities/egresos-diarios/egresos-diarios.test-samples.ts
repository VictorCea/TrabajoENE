import { IEgresosDiarios, NewEgresosDiarios } from './egresos-diarios.model';

export const sampleWithRequiredData: IEgresosDiarios = {
  id: 18472,
};

export const sampleWithPartialData: IEgresosDiarios = {
  id: 7602,
};

export const sampleWithFullData: IEgresosDiarios = {
  id: 17781,
  gastoDiario: 'beneath for publicity',
  valorGasto: 10892,
};

export const sampleWithNewData: NewEgresosDiarios = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
