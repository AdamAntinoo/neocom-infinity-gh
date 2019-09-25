// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
    production: false,
    mockStatus: true,
    showexceptions: true,
    serverName: "http://localhost:4200",
    copyright: '© 2019,2020 Dimensinfin Industries',
    appName: require('../../package.json').name,
    appVersion: require('../../package.json').version + " dev",
    apiVersion1: '/api/v1/neocom',
    apiVersion2: '/api/v1/neocom',
    ESIDataSource: 'Tranquility',
    // - C O N S T A N T S
    JWTTOKEN_KEY: '-JWTTOKEN_KEY-',
    VALID_STATE: 'LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct'
};

import 'zone.js/dist/zone-error';  // Included with Angular CLI.
