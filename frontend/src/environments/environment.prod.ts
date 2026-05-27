import { NeightConfiguration } from "src/neight.config";

// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const neightEnvironment: NeightConfiguration = new NeightConfiguration();
neightEnvironment.production = true;
neightEnvironment.api_url = 'https://neight-app.com/neight-api/';