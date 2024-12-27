export interface IProveedor {
  id: number;
  nombre?: string | null;
  direccion?: string | null;
  email?: string | null;
  fono?: string | null;
}

export type NewProveedor = Omit<IProveedor, 'id'> & { id: null };
