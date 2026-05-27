import { Inject, Injectable } from '@angular/core';
import { NeightConfiguration, NEIGHT_CONFIG } from './neight.config';

@Injectable()
export class NeightApiService {

    constructor(@Inject(NEIGHT_CONFIG) private neightConfig: NeightConfiguration) { }

    public getBackendUrl(): string {
        return this.neightConfig.api_url;
    }
}