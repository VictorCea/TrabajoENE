export interface IPedidosProveedores {
  id: number;
  producto?: string | null;
  cantidad?: number | null;
  valor?: number | null;
}

export type NewPedidosProveedores = Omit<IPedidosProveedores, 'id'> & { id: null };
