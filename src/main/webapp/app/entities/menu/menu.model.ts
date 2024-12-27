export interface IMenu {
  id: number;
  nombrePlato?: string | null;
  valorPlato?: number | null;
  descripcionPlato?: string | null;
}

export type NewMenu = Omit<IMenu, 'id'> & { id: null };
