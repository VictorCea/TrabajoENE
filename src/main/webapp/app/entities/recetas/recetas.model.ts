export interface IRecetas {
  id: number;
  nombrePlato?: string | null;
  ingredientesPlato?: string | null;
  tiempoPreparacion?: string | null;
}

export type NewRecetas = Omit<IRecetas, 'id'> & { id: null };
