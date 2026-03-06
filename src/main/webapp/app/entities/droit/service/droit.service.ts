import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDroit, NewDroit } from '../../../shared/model/security/droit.model';

export type PartialUpdateDroit = Partial<IDroit> & Pick<IDroit, 'id'>;

export type EntityResponseType = HttpResponse<IDroit>;
export type EntityArrayResponseType = HttpResponse<IDroit[]>;

@Injectable({ providedIn: 'root' })
export class DroitService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/droits');

  create(droit: NewDroit): Observable<EntityResponseType> {
    return this.http.post<IDroit>(this.resourceUrl, droit, { observe: 'response' });
  }

  update(droit: IDroit): Observable<EntityResponseType> {
    return this.http.put<IDroit>(`${this.resourceUrl}/${this.getDroitIdentifier(droit)}`, droit, { observe: 'response' });
  }

  partialUpdate(droit: PartialUpdateDroit): Observable<EntityResponseType> {
    return this.http.patch<IDroit>(`${this.resourceUrl}/${this.getDroitIdentifier(droit)}`, droit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDroit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDroit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDroitIdentifier(droit: Pick<IDroit, 'id'>): number {
    return droit.id;
  }

  compareDroit(o1: Pick<IDroit, 'id'> | null, o2: Pick<IDroit, 'id'> | null): boolean {
    return o1 && o2 ? this.getDroitIdentifier(o1) === this.getDroitIdentifier(o2) : o1 === o2;
  }

  addDroitToCollectionIfMissing<Type extends Pick<IDroit, 'id'>>(
    droitCollection: Type[],
    ...droitsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const droits: Type[] = droitsToCheck.filter(isPresent);
    if (droits.length > 0) {
      const droitCollectionIdentifiers = droitCollection.map(droitItem => this.getDroitIdentifier(droitItem));
      const droitsToAdd = droits.filter(droitItem => {
        const droitIdentifier = this.getDroitIdentifier(droitItem);
        if (droitCollectionIdentifiers.includes(droitIdentifier)) {
          return false;
        }
        droitCollectionIdentifiers.push(droitIdentifier);
        return true;
      });
      return [...droitsToAdd, ...droitCollection];
    }
    return droitCollection;
  }
}
