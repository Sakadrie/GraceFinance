import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAffectationUtilisateur } from '../affectation-utilisateur.model';
import { AffectationUtilisateurService } from '../service/affectation-utilisateur.service';

const affectationUtilisateurResolve = (route: ActivatedRouteSnapshot): Observable<null | IAffectationUtilisateur> => {
  const id = route.params.id;
  if (id) {
    return inject(AffectationUtilisateurService)
      .find(id)
      .pipe(
        mergeMap((affectationUtilisateur: HttpResponse<IAffectationUtilisateur>) => {
          if (affectationUtilisateur.body) {
            return of(affectationUtilisateur.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default affectationUtilisateurResolve;
