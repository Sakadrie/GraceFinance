import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompteComptable } from '../../../shared/model/principal/compte-comptable.model';
import { CompteComptableService } from '../service/compte-comptable.service';

const compteComptableResolve = (route: ActivatedRouteSnapshot): Observable<null | ICompteComptable> => {
  const id = route.params.id;
  if (id) {
    return inject(CompteComptableService)
      .find(id)
      .pipe(
        mergeMap((compteComptable: HttpResponse<ICompteComptable>) => {
          if (compteComptable.body) {
            return of(compteComptable.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default compteComptableResolve;
