import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEgresosDiarios, NewEgresosDiarios } from '../egresos-diarios.model';

export type PartialUpdateEgresosDiarios = Partial<IEgresosDiarios> & Pick<IEgresosDiarios, 'id'>;

export type EntityResponseType = HttpResponse<IEgresosDiarios>;
export type EntityArrayResponseType = HttpResponse<IEgresosDiarios[]>;

@Injectable({ providedIn: 'root' })
export class EgresosDiariosService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/egresos-diarios');

  create(egresosDiarios: NewEgresosDiarios): Observable<EntityResponseType> {
    return this.http.post<IEgresosDiarios>(this.resourceUrl, egresosDiarios, { observe: 'response' });
  }

  update(egresosDiarios: IEgresosDiarios): Observable<EntityResponseType> {
    return this.http.put<IEgresosDiarios>(`${this.resourceUrl}/${this.getEgresosDiariosIdentifier(egresosDiarios)}`, egresosDiarios, {
      observe: 'response',
    });
  }

  partialUpdate(egresosDiarios: PartialUpdateEgresosDiarios): Observable<EntityResponseType> {
    return this.http.patch<IEgresosDiarios>(`${this.resourceUrl}/${this.getEgresosDiariosIdentifier(egresosDiarios)}`, egresosDiarios, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEgresosDiarios>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEgresosDiarios[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEgresosDiariosIdentifier(egresosDiarios: Pick<IEgresosDiarios, 'id'>): number {
    return egresosDiarios.id;
  }

  compareEgresosDiarios(o1: Pick<IEgresosDiarios, 'id'> | null, o2: Pick<IEgresosDiarios, 'id'> | null): boolean {
    return o1 && o2 ? this.getEgresosDiariosIdentifier(o1) === this.getEgresosDiariosIdentifier(o2) : o1 === o2;
  }

  addEgresosDiariosToCollectionIfMissing<Type extends Pick<IEgresosDiarios, 'id'>>(
    egresosDiariosCollection: Type[],
    ...egresosDiariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const egresosDiarios: Type[] = egresosDiariosToCheck.filter(isPresent);
    if (egresosDiarios.length > 0) {
      const egresosDiariosCollectionIdentifiers = egresosDiariosCollection.map(egresosDiariosItem =>
        this.getEgresosDiariosIdentifier(egresosDiariosItem),
      );
      const egresosDiariosToAdd = egresosDiarios.filter(egresosDiariosItem => {
        const egresosDiariosIdentifier = this.getEgresosDiariosIdentifier(egresosDiariosItem);
        if (egresosDiariosCollectionIdentifiers.includes(egresosDiariosIdentifier)) {
          return false;
        }
        egresosDiariosCollectionIdentifiers.push(egresosDiariosIdentifier);
        return true;
      });
      return [...egresosDiariosToAdd, ...egresosDiariosCollection];
    }
    return egresosDiariosCollection;
  }
}
