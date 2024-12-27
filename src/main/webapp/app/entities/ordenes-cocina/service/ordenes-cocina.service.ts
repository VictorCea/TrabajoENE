import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrdenesCocina, NewOrdenesCocina } from '../ordenes-cocina.model';

export type PartialUpdateOrdenesCocina = Partial<IOrdenesCocina> & Pick<IOrdenesCocina, 'id'>;

export type EntityResponseType = HttpResponse<IOrdenesCocina>;
export type EntityArrayResponseType = HttpResponse<IOrdenesCocina[]>;

@Injectable({ providedIn: 'root' })
export class OrdenesCocinaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ordenes-cocinas');

  create(ordenesCocina: NewOrdenesCocina): Observable<EntityResponseType> {
    return this.http.post<IOrdenesCocina>(this.resourceUrl, ordenesCocina, { observe: 'response' });
  }

  update(ordenesCocina: IOrdenesCocina): Observable<EntityResponseType> {
    return this.http.put<IOrdenesCocina>(`${this.resourceUrl}/${this.getOrdenesCocinaIdentifier(ordenesCocina)}`, ordenesCocina, {
      observe: 'response',
    });
  }

  partialUpdate(ordenesCocina: PartialUpdateOrdenesCocina): Observable<EntityResponseType> {
    return this.http.patch<IOrdenesCocina>(`${this.resourceUrl}/${this.getOrdenesCocinaIdentifier(ordenesCocina)}`, ordenesCocina, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrdenesCocina>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrdenesCocina[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrdenesCocinaIdentifier(ordenesCocina: Pick<IOrdenesCocina, 'id'>): number {
    return ordenesCocina.id;
  }

  compareOrdenesCocina(o1: Pick<IOrdenesCocina, 'id'> | null, o2: Pick<IOrdenesCocina, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrdenesCocinaIdentifier(o1) === this.getOrdenesCocinaIdentifier(o2) : o1 === o2;
  }

  addOrdenesCocinaToCollectionIfMissing<Type extends Pick<IOrdenesCocina, 'id'>>(
    ordenesCocinaCollection: Type[],
    ...ordenesCocinasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ordenesCocinas: Type[] = ordenesCocinasToCheck.filter(isPresent);
    if (ordenesCocinas.length > 0) {
      const ordenesCocinaCollectionIdentifiers = ordenesCocinaCollection.map(ordenesCocinaItem =>
        this.getOrdenesCocinaIdentifier(ordenesCocinaItem),
      );
      const ordenesCocinasToAdd = ordenesCocinas.filter(ordenesCocinaItem => {
        const ordenesCocinaIdentifier = this.getOrdenesCocinaIdentifier(ordenesCocinaItem);
        if (ordenesCocinaCollectionIdentifiers.includes(ordenesCocinaIdentifier)) {
          return false;
        }
        ordenesCocinaCollectionIdentifiers.push(ordenesCocinaIdentifier);
        return true;
      });
      return [...ordenesCocinasToAdd, ...ordenesCocinaCollection];
    }
    return ordenesCocinaCollection;
  }
}
