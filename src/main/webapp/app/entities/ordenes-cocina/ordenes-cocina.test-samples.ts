import { IOrdenesCocina, NewOrdenesCocina } from './ordenes-cocina.model';

export const sampleWithRequiredData: IOrdenesCocina = {
  id: 32081,
};

export const sampleWithPartialData: IOrdenesCocina = {
  id: 19946,
  cantidadPlato: 23627,
};

export const sampleWithFullData: IOrdenesCocina = {
  id: 19120,
  nombrePlato: 'unfreeze',
  cantidadPlato: 19095,
};

export const sampleWithNewData: NewOrdenesCocina = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
