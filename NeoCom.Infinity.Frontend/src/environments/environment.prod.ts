export const environment = {
    production: true,
    mockStatus: false,
    showexceptions: false,
    serverName: "https://neocom-backend.herokuapp.com",
    copyright: '© 2019,2020 Dimensinfin Industries',
    appName: require('../../package.json').name,
    appVersion: require('../../package.json').version,
    platform: 'Angular 8.2.3 - RxJs 6.4.0 - Rollbar 2.13',
    apiVersion1: '/api/v1/neocom',
    apiVersion2: '/api/v2/neocom',
    ESIDataSource: 'Tranquility',
    LoginRequest: 'https://login.eveonline.com/v2/oauth/authorize/?response_type=code&client_id=98eb8d31c5d24649ba4f7eb015596fbd&state=LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct&redirect_uri=http%3A%2F%2Fneocom-infinity.herokuapp.com%2Fapp%2FloginValidation',
    // - C O N S T A N T S
    JWTTOKEN_KEY: '-JWTTOKEN_KEY-',
    JWTTOKEN_EXPIRATION_TIME_KEY: '-JWTTOKEN_EXPIRATION_TIME_KEY-',
    CREDENTIAL_KEY: '-CREDENTIAL-KEY-',
    VALID_STATE: 'LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct',
    DEFAULT_ICON_PLACEHOLDER: '/assets/media/defaulticonplaceholder.png',
    DEFAULT_AVATAR_PLACEHOLDER: '/assets/media/defaultavatarplaceholder.png'
};
