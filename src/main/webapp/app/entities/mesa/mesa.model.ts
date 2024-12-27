export interface IMesa {
  id: number;
  numero?: number | null;
}

export type NewMesa = Omit<IMesa, 'id'> & { id: null };
