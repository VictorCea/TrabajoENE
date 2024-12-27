export interface IEgresosDiarios {
  id: number;
  gastoDiario?: string | null;
  valorGasto?: number | null;
}

export type NewEgresosDiarios = Omit<IEgresosDiarios, 'id'> & { id: null };
