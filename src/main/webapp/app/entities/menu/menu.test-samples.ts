import { IMenu, NewMenu } from './menu.model';

export const sampleWithRequiredData: IMenu = {
  id: 26476,
};

export const sampleWithPartialData: IMenu = {
  id: 29015,
  nombrePlato: 'handful',
  valorPlato: 3945,
};

export const sampleWithFullData: IMenu = {
  id: 277,
  nombrePlato: 'wilt honesty',
  valorPlato: 7220,
  descripcionPlato: 'cake',
};

export const sampleWithNewData: NewMenu = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
