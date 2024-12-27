export interface IProducto {
  id: number;
  nombre?: string | null;
  stock?: number | null;
}

export type NewProducto = Omit<IProducto, 'id'> & { id: null };
