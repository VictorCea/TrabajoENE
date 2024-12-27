import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRecetas, NewRecetas } from '../recetas.model';

export type PartialUpdateRecetas = Partial<IRecetas> & Pick<IRecetas, 'id'>;

export type EntityResponseType = HttpResponse<IRecetas>;
export type EntityArrayResponseType = HttpResponse<IRecetas[]>;

@Injectable({ providedIn: 'root' })
export class RecetasService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/recetas');

  create(recetas: NewRecetas): Observable<EntityResponseType> {
    return this.http.post<IRecetas>(this.resourceUrl, recetas, { observe: 'response' });
  }

  update(recetas: IRecetas): Observable<EntityResponseType> {
    return this.http.put<IRecetas>(`${this.resourceUrl}/${this.getRecetasIdentifier(recetas)}`, recetas, { observe: 'response' });
  }

  partialUpdate(recetas: PartialUpdateRecetas): Observable<EntityResponseType> {
    return this.http.patch<IRecetas>(`${this.resourceUrl}/${this.getRecetasIdentifier(recetas)}`, recetas, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRecetas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRecetas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRecetasIdentifier(recetas: Pick<IRecetas, 'id'>): number {
    return recetas.id;
  }

  compareRecetas(o1: Pick<IRecetas, 'id'> | null, o2: Pick<IRecetas, 'id'> | null): boolean {
    return o1 && o2 ? this.getRecetasIdentifier(o1) === this.getRecetasIdentifier(o2) : o1 === o2;
  }

  addRecetasToCollectionIfMissing<Type extends Pick<IRecetas, 'id'>>(
    recetasCollection: Type[],
    ...recetasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const recetas: Type[] = recetasToCheck.filter(isPresent);
    if (recetas.length > 0) {
      const recetasCollectionIdentifiers = recetasCollection.map(recetasItem => this.getRecetasIdentifier(recetasItem));
      const recetasToAdd = recetas.filter(recetasItem => {
        const recetasIdentifier = this.getRecetasIdentifier(recetasItem);
        if (recetasCollectionIdentifiers.includes(recetasIdentifier)) {
          return false;
        }
        recetasCollectionIdentifiers.push(recetasIdentifier);
        return true;
      });
      return [...recetasToAdd, ...recetasCollection];
    }
    return recetasCollection;
  }
}
