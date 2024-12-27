import { IUsuario, NewUsuario } from './usuario.model';

export const sampleWithRequiredData: IUsuario = {
  id: 23732,
};

export const sampleWithPartialData: IUsuario = {
  id: 19924,
  rol: 'outrun',
  admin: false,
};

export const sampleWithFullData: IUsuario = {
  id: 32608,
  nombre: 'outlaw lest',
  rol: 'nocturnal yum up',
  rut: 'outlandish',
  admin: true,
};

export const sampleWithNewData: NewUsuario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
