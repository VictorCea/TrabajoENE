import { IProveedor, NewProveedor } from './proveedor.model';

export const sampleWithRequiredData: IProveedor = {
  id: 1215,
};

export const sampleWithPartialData: IProveedor = {
  id: 20935,
  direccion: 'that',
  email: 'Agustin48@hotmail.com',
};

export const sampleWithFullData: IProveedor = {
  id: 2129,
  nombre: 'hence contravene those',
  direccion: 'blah geez off',
  email: 'Maica.QuesadaRomero@yahoo.com',
  fono: 'question',
};

export const sampleWithNewData: NewProveedor = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
