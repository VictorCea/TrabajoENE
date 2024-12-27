import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRecetas } from '../recetas.model';
import { RecetasService } from '../service/recetas.service';

const recetasResolve = (route: ActivatedRouteSnapshot): Observable<null | IRecetas> => {
  const id = route.params.id;
  if (id) {
    return inject(RecetasService)
      .find(id)
      .pipe(
        mergeMap((recetas: HttpResponse<IRecetas>) => {
          if (recetas.body) {
            return of(recetas.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default recetasResolve;
