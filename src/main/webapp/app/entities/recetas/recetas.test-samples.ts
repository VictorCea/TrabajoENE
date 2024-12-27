import { IRecetas, NewRecetas } from './recetas.model';

export const sampleWithRequiredData: IRecetas = {
  id: 8501,
};

export const sampleWithPartialData: IRecetas = {
  id: 21555,
  nombrePlato: 'french',
  ingredientesPlato: 'blue worth',
};

export const sampleWithFullData: IRecetas = {
  id: 3767,
  nombrePlato: 'awareness',
  ingredientesPlato: 'scornful phooey bony',
  tiempoPreparacion: 'ornery playfully on',
};

export const sampleWithNewData: NewRecetas = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
