// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  copyright : '© 2019,2020 Dimensinfin Industries',
  appName: require('../../package.json').name,
  appVersion: require('../../package.json').version + " dev",
  backendServerHost: 'http://localhost',
  backendServerPort: ':6091',
  prefixApiV1: '/api/v1/neocom/'
};

import 'zone.js/dist/zone-error';  // Included with Angular CLI.
