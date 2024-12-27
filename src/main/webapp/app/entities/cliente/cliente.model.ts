export interface ICliente {
  id: number;
  mesa?: number | null;
  orden?: string | null;
  cantidadClientes?: number | null;
}

export type NewCliente = Omit<ICliente, 'id'> & { id: null };
