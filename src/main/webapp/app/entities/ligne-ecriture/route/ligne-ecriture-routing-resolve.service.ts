import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILigneEcriture } from '../ligne-ecriture.model';
import { LigneEcritureService } from '../service/ligne-ecriture.service';

const ligneEcritureResolve = (route: ActivatedRouteSnapshot): Observable<null | ILigneEcriture> => {
  const id = route.params.id;
  if (id) {
    return inject(LigneEcritureService)
      .find(id)
      .pipe(
        mergeMap((ligneEcriture: HttpResponse<ILigneEcriture>) => {
          if (ligneEcriture.body) {
            return of(ligneEcriture.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default ligneEcritureResolve;
