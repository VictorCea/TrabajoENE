import { IMesa, NewMesa } from './mesa.model';

export const sampleWithRequiredData: IMesa = {
  id: 31066,
};

export const sampleWithPartialData: IMesa = {
  id: 6713,
  numero: 24943,
};

export const sampleWithFullData: IMesa = {
  id: 26759,
  numero: 11540,
};

export const sampleWithNewData: NewMesa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
