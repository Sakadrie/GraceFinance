import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILigneEcriture, NewLigneEcriture } from '../ligne-ecriture.model';

export type PartialUpdateLigneEcriture = Partial<ILigneEcriture> & Pick<ILigneEcriture, 'id'>;

export type EntityResponseType = HttpResponse<ILigneEcriture>;
export type EntityArrayResponseType = HttpResponse<ILigneEcriture[]>;

@Injectable({ providedIn: 'root' })
export class LigneEcritureService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ligne-ecritures');

  create(ligneEcriture: NewLigneEcriture): Observable<EntityResponseType> {
    return this.http.post<ILigneEcriture>(this.resourceUrl, ligneEcriture, { observe: 'response' });
  }

  update(ligneEcriture: ILigneEcriture): Observable<EntityResponseType> {
    return this.http.put<ILigneEcriture>(`${this.resourceUrl}/${this.getLigneEcritureIdentifier(ligneEcriture)}`, ligneEcriture, {
      observe: 'response',
    });
  }

  partialUpdate(ligneEcriture: PartialUpdateLigneEcriture): Observable<EntityResponseType> {
    return this.http.patch<ILigneEcriture>(`${this.resourceUrl}/${this.getLigneEcritureIdentifier(ligneEcriture)}`, ligneEcriture, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILigneEcriture>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILigneEcriture[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLigneEcritureIdentifier(ligneEcriture: Pick<ILigneEcriture, 'id'>): number {
    return ligneEcriture.id;
  }

  compareLigneEcriture(o1: Pick<ILigneEcriture, 'id'> | null, o2: Pick<ILigneEcriture, 'id'> | null): boolean {
    return o1 && o2 ? this.getLigneEcritureIdentifier(o1) === this.getLigneEcritureIdentifier(o2) : o1 === o2;
  }

  addLigneEcritureToCollectionIfMissing<Type extends Pick<ILigneEcriture, 'id'>>(
    ligneEcritureCollection: Type[],
    ...ligneEcrituresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ligneEcritures: Type[] = ligneEcrituresToCheck.filter(isPresent);
    if (ligneEcritures.length > 0) {
      const ligneEcritureCollectionIdentifiers = ligneEcritureCollection.map(ligneEcritureItem =>
        this.getLigneEcritureIdentifier(ligneEcritureItem),
      );
      const ligneEcrituresToAdd = ligneEcritures.filter(ligneEcritureItem => {
        const ligneEcritureIdentifier = this.getLigneEcritureIdentifier(ligneEcritureItem);
        if (ligneEcritureCollectionIdentifiers.includes(ligneEcritureIdentifier)) {
          return false;
        }
        ligneEcritureCollectionIdentifiers.push(ligneEcritureIdentifier);
        return true;
      });
      return [...ligneEcrituresToAdd, ...ligneEcritureCollection];
    }
    return ligneEcritureCollection;
  }
}
