import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { AnnonService } from './annon.service';
import { Observable } from 'rxjs';

export interface IAnnonMatches {
    matches: any[];
    title: string;
}

@Injectable()
export class AnnonRouteResolverService implements Resolve<IAnnonMatches> {
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): IAnnonMatches | Observable<IAnnonMatches> | Promise<IAnnonMatches> {
        let prefs: any;
        prefs.gender = route.params['gender'];
        prefs.prefGen = route.params['prefGender'];
        prefs.prefAge = route.params['prefAge'];
        prefs.city = route.params['city'];
        prefs.tag = route.params['tag'];
        console.log('options ::' + JSON.stringify(prefs));
        return null;
    }

    constructor(private annonService: AnnonService) {}
}
