export interface IOrdenesCocina {
  id: number;
  nombrePlato?: string | null;
  cantidadPlato?: number | null;
}

export type NewOrdenesCocina = Omit<IOrdenesCocina, 'id'> & { id: null };
