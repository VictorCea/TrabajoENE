import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIngresosDiarios, NewIngresosDiarios } from '../ingresos-diarios.model';

export type PartialUpdateIngresosDiarios = Partial<IIngresosDiarios> & Pick<IIngresosDiarios, 'id'>;

export type EntityResponseType = HttpResponse<IIngresosDiarios>;
export type EntityArrayResponseType = HttpResponse<IIngresosDiarios[]>;

@Injectable({ providedIn: 'root' })
export class IngresosDiariosService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ingresos-diarios');

  create(ingresosDiarios: NewIngresosDiarios): Observable<EntityResponseType> {
    return this.http.post<IIngresosDiarios>(this.resourceUrl, ingresosDiarios, { observe: 'response' });
  }

  update(ingresosDiarios: IIngresosDiarios): Observable<EntityResponseType> {
    return this.http.put<IIngresosDiarios>(`${this.resourceUrl}/${this.getIngresosDiariosIdentifier(ingresosDiarios)}`, ingresosDiarios, {
      observe: 'response',
    });
  }

  partialUpdate(ingresosDiarios: PartialUpdateIngresosDiarios): Observable<EntityResponseType> {
    return this.http.patch<IIngresosDiarios>(`${this.resourceUrl}/${this.getIngresosDiariosIdentifier(ingresosDiarios)}`, ingresosDiarios, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIngresosDiarios>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIngresosDiarios[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIngresosDiariosIdentifier(ingresosDiarios: Pick<IIngresosDiarios, 'id'>): number {
    return ingresosDiarios.id;
  }

  compareIngresosDiarios(o1: Pick<IIngresosDiarios, 'id'> | null, o2: Pick<IIngresosDiarios, 'id'> | null): boolean {
    return o1 && o2 ? this.getIngresosDiariosIdentifier(o1) === this.getIngresosDiariosIdentifier(o2) : o1 === o2;
  }

  addIngresosDiariosToCollectionIfMissing<Type extends Pick<IIngresosDiarios, 'id'>>(
    ingresosDiariosCollection: Type[],
    ...ingresosDiariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ingresosDiarios: Type[] = ingresosDiariosToCheck.filter(isPresent);
    if (ingresosDiarios.length > 0) {
      const ingresosDiariosCollectionIdentifiers = ingresosDiariosCollection.map(ingresosDiariosItem =>
        this.getIngresosDiariosIdentifier(ingresosDiariosItem),
      );
      const ingresosDiariosToAdd = ingresosDiarios.filter(ingresosDiariosItem => {
        const ingresosDiariosIdentifier = this.getIngresosDiariosIdentifier(ingresosDiariosItem);
        if (ingresosDiariosCollectionIdentifiers.includes(ingresosDiariosIdentifier)) {
          return false;
        }
        ingresosDiariosCollectionIdentifiers.push(ingresosDiariosIdentifier);
        return true;
      });
      return [...ingresosDiariosToAdd, ...ingresosDiariosCollection];
    }
    return ingresosDiariosCollection;
  }
}
