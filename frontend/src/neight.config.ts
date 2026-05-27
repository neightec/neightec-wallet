import { InjectionToken } from '@angular/core';

export const NEIGHT_CONFIG = new InjectionToken<NeightConfiguration>('NEIGHT_CONFIG');

export class NeightConfiguration { 
    production: boolean;
    api_url: string;
}
