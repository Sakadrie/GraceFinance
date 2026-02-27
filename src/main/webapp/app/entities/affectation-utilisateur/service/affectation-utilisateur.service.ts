import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAffectationUtilisateur, NewAffectationUtilisateur } from '../affectation-utilisateur.model';

export type PartialUpdateAffectationUtilisateur = Partial<IAffectationUtilisateur> & Pick<IAffectationUtilisateur, 'id'>;

type RestOf<T extends IAffectationUtilisateur | NewAffectationUtilisateur> = Omit<T, 'dateAffectation'> & {
  dateAffectation?: string | null;
};

export type RestAffectationUtilisateur = RestOf<IAffectationUtilisateur>;

export type NewRestAffectationUtilisateur = RestOf<NewAffectationUtilisateur>;

export type PartialUpdateRestAffectationUtilisateur = RestOf<PartialUpdateAffectationUtilisateur>;

export type EntityResponseType = HttpResponse<IAffectationUtilisateur>;
export type EntityArrayResponseType = HttpResponse<IAffectationUtilisateur[]>;

@Injectable({ providedIn: 'root' })
export class AffectationUtilisateurService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/affectation-utilisateurs');

  create(affectationUtilisateur: NewAffectationUtilisateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affectationUtilisateur);
    return this.http
      .post<RestAffectationUtilisateur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(affectationUtilisateur: IAffectationUtilisateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affectationUtilisateur);
    return this.http
      .put<RestAffectationUtilisateur>(`${this.resourceUrl}/${this.getAffectationUtilisateurIdentifier(affectationUtilisateur)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(affectationUtilisateur: PartialUpdateAffectationUtilisateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(affectationUtilisateur);
    return this.http
      .patch<RestAffectationUtilisateur>(`${this.resourceUrl}/${this.getAffectationUtilisateurIdentifier(affectationUtilisateur)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAffectationUtilisateur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAffectationUtilisateur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAffectationUtilisateurIdentifier(affectationUtilisateur: Pick<IAffectationUtilisateur, 'id'>): number {
    return affectationUtilisateur.id;
  }

  compareAffectationUtilisateur(o1: Pick<IAffectationUtilisateur, 'id'> | null, o2: Pick<IAffectationUtilisateur, 'id'> | null): boolean {
    return o1 && o2 ? this.getAffectationUtilisateurIdentifier(o1) === this.getAffectationUtilisateurIdentifier(o2) : o1 === o2;
  }

  addAffectationUtilisateurToCollectionIfMissing<Type extends Pick<IAffectationUtilisateur, 'id'>>(
    affectationUtilisateurCollection: Type[],
    ...affectationUtilisateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const affectationUtilisateurs: Type[] = affectationUtilisateursToCheck.filter(isPresent);
    if (affectationUtilisateurs.length > 0) {
      const affectationUtilisateurCollectionIdentifiers = affectationUtilisateurCollection.map(affectationUtilisateurItem =>
        this.getAffectationUtilisateurIdentifier(affectationUtilisateurItem),
      );
      const affectationUtilisateursToAdd = affectationUtilisateurs.filter(affectationUtilisateurItem => {
        const affectationUtilisateurIdentifier = this.getAffectationUtilisateurIdentifier(affectationUtilisateurItem);
        if (affectationUtilisateurCollectionIdentifiers.includes(affectationUtilisateurIdentifier)) {
          return false;
        }
        affectationUtilisateurCollectionIdentifiers.push(affectationUtilisateurIdentifier);
        return true;
      });
      return [...affectationUtilisateursToAdd, ...affectationUtilisateurCollection];
    }
    return affectationUtilisateurCollection;
  }

  protected convertDateFromClient<T extends IAffectationUtilisateur | NewAffectationUtilisateur | PartialUpdateAffectationUtilisateur>(
    affectationUtilisateur: T,
  ): RestOf<T> {
    return {
      ...affectationUtilisateur,
      dateAffectation: affectationUtilisateur.dateAffectation?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAffectationUtilisateur: RestAffectationUtilisateur): IAffectationUtilisateur {
    return {
      ...restAffectationUtilisateur,
      dateAffectation: restAffectationUtilisateur.dateAffectation ? dayjs(restAffectationUtilisateur.dateAffectation) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAffectationUtilisateur>): HttpResponse<IAffectationUtilisateur> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAffectationUtilisateur[]>): HttpResponse<IAffectationUtilisateur[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
