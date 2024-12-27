import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPedidosProveedores, NewPedidosProveedores } from '../pedidos-proveedores.model';

export type PartialUpdatePedidosProveedores = Partial<IPedidosProveedores> & Pick<IPedidosProveedores, 'id'>;

export type EntityResponseType = HttpResponse<IPedidosProveedores>;
export type EntityArrayResponseType = HttpResponse<IPedidosProveedores[]>;

@Injectable({ providedIn: 'root' })
export class PedidosProveedoresService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pedidos-proveedores');

  create(pedidosProveedores: NewPedidosProveedores): Observable<EntityResponseType> {
    return this.http.post<IPedidosProveedores>(this.resourceUrl, pedidosProveedores, { observe: 'response' });
  }

  update(pedidosProveedores: IPedidosProveedores): Observable<EntityResponseType> {
    return this.http.put<IPedidosProveedores>(
      `${this.resourceUrl}/${this.getPedidosProveedoresIdentifier(pedidosProveedores)}`,
      pedidosProveedores,
      { observe: 'response' },
    );
  }

  partialUpdate(pedidosProveedores: PartialUpdatePedidosProveedores): Observable<EntityResponseType> {
    return this.http.patch<IPedidosProveedores>(
      `${this.resourceUrl}/${this.getPedidosProveedoresIdentifier(pedidosProveedores)}`,
      pedidosProveedores,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPedidosProveedores>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPedidosProveedores[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPedidosProveedoresIdentifier(pedidosProveedores: Pick<IPedidosProveedores, 'id'>): number {
    return pedidosProveedores.id;
  }

  comparePedidosProveedores(o1: Pick<IPedidosProveedores, 'id'> | null, o2: Pick<IPedidosProveedores, 'id'> | null): boolean {
    return o1 && o2 ? this.getPedidosProveedoresIdentifier(o1) === this.getPedidosProveedoresIdentifier(o2) : o1 === o2;
  }

  addPedidosProveedoresToCollectionIfMissing<Type extends Pick<IPedidosProveedores, 'id'>>(
    pedidosProveedoresCollection: Type[],
    ...pedidosProveedoresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pedidosProveedores: Type[] = pedidosProveedoresToCheck.filter(isPresent);
    if (pedidosProveedores.length > 0) {
      const pedidosProveedoresCollectionIdentifiers = pedidosProveedoresCollection.map(pedidosProveedoresItem =>
        this.getPedidosProveedoresIdentifier(pedidosProveedoresItem),
      );
      const pedidosProveedoresToAdd = pedidosProveedores.filter(pedidosProveedoresItem => {
        const pedidosProveedoresIdentifier = this.getPedidosProveedoresIdentifier(pedidosProveedoresItem);
        if (pedidosProveedoresCollectionIdentifiers.includes(pedidosProveedoresIdentifier)) {
          return false;
        }
        pedidosProveedoresCollectionIdentifiers.push(pedidosProveedoresIdentifier);
        return true;
      });
      return [...pedidosProveedoresToAdd, ...pedidosProveedoresCollection];
    }
    return pedidosProveedoresCollection;
  }
}
