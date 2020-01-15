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
    appSignature: "S000.016.001-20191023",
    platform: 'Angular 8.2.3 - RxJs 6.4.0 - Rollbar 2.13',
    apiVersion1: '/api/v1/neocom',
    apiVersion2: '/api/v2/neocom',
    ESIDataSource: 'Tranquility',
    LoginRequest: 'https://login.eveonline.com/v2/oauth/authorize/?response_type=code&client_id=eacaa9cd36594189877544d851753734&state=LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct&redirect_uri=http%3A%2F%2Fneocom.infinity.local%2Fapp%2FloginValidation',
    // - C O N S T A N T S
    JWTTOKEN_KEY: '-JWTTOKEN_KEY-',
    JWTTOKEN_EXPIRATION_TIME_KEY: '-JWTTOKEN_EXPIRATION_TIME_KEY-',
    CREDENTIAL_KEY: '-CREDENTIAL-KEY-',
    VALID_STATE: 'LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct',
    DEFAULT_ICON_PLACEHOLDER: '/assets/media/defaulticonplaceholder.png',
    DEFAULT_AVATAR_PLACEHOLDER: '/assets/media/defaultavatarplaceholder.png'
};

import 'zone.js/dist/zone-error';  // Included with Angular CLI.
