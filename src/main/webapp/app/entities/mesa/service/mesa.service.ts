import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMesa, NewMesa } from '../mesa.model';

export type PartialUpdateMesa = Partial<IMesa> & Pick<IMesa, 'id'>;

export type EntityResponseType = HttpResponse<IMesa>;
export type EntityArrayResponseType = HttpResponse<IMesa[]>;

@Injectable({ providedIn: 'root' })
export class MesaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mesas');

  create(mesa: NewMesa): Observable<EntityResponseType> {
    return this.http.post<IMesa>(this.resourceUrl, mesa, { observe: 'response' });
  }

  update(mesa: IMesa): Observable<EntityResponseType> {
    return this.http.put<IMesa>(`${this.resourceUrl}/${this.getMesaIdentifier(mesa)}`, mesa, { observe: 'response' });
  }

  partialUpdate(mesa: PartialUpdateMesa): Observable<EntityResponseType> {
    return this.http.patch<IMesa>(`${this.resourceUrl}/${this.getMesaIdentifier(mesa)}`, mesa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMesa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMesa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMesaIdentifier(mesa: Pick<IMesa, 'id'>): number {
    return mesa.id;
  }

  compareMesa(o1: Pick<IMesa, 'id'> | null, o2: Pick<IMesa, 'id'> | null): boolean {
    return o1 && o2 ? this.getMesaIdentifier(o1) === this.getMesaIdentifier(o2) : o1 === o2;
  }

  addMesaToCollectionIfMissing<Type extends Pick<IMesa, 'id'>>(
    mesaCollection: Type[],
    ...mesasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mesas: Type[] = mesasToCheck.filter(isPresent);
    if (mesas.length > 0) {
      const mesaCollectionIdentifiers = mesaCollection.map(mesaItem => this.getMesaIdentifier(mesaItem));
      const mesasToAdd = mesas.filter(mesaItem => {
        const mesaIdentifier = this.getMesaIdentifier(mesaItem);
        if (mesaCollectionIdentifiers.includes(mesaIdentifier)) {
          return false;
        }
        mesaCollectionIdentifiers.push(mesaIdentifier);
        return true;
      });
      return [...mesasToAdd, ...mesaCollection];
    }
    return mesaCollection;
  }
}
