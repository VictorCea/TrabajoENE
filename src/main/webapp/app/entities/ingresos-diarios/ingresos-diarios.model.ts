export interface IIngresosDiarios {
  id: number;
  ventaNumero?: number | null;
  valorVenta?: number | null;
}

export type NewIngresosDiarios = Omit<IIngresosDiarios, 'id'> & { id: null };
