it('loads', () => {
    // sessionStorage.setItem('-CREDENTIAL-KEY-', '"{\"jsonClass\":\"Credential\",\"assetsCount\":100,\"walletBalance\":654987,\"miningResourcesEstimatedValue\":345234,\"uniqueId\":\"tranquility/92002067\",\"accountId\":92002067,\"accountName\":\"Adam Antinoo\",\"dataSource\":\"tranquility\",\"corporationId\":1427661573,\"raceName\":\"Minmatar\"}"')
    // sessionStorage.setItem('-JWTTOKEN-KEY-', '"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6MTQyNzY2MTU3MywiYWNjb3VudE5hbWUiOiJBZGFtIEFudGlub28iLCJpc3MiOiJOZW9Db20uSW5maW5pdHkuQmFja2VuZCIsInVuaXF1ZUlkIjoidHJhbnF1aWxpdHkvOTIwMDIwNjciLCJwaWxvdElkIjo5MjAwMjA2N30.6JgBvtHyhvD8aY8-I4075tb433mYMpn9sNeYCkIO28LbhqVR4CZ-x1t_sk4IOLLtzSN07bF4c7ZceWw_ta4Brw"')
    cy.visit('http://neocom.infinity.local/app/loginValidation?code=b__Y5_Btn0ens8j_QviKFQ&state=LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct');
    cy.visit('http://neocom.infinity.local/app/dashboard');
    // });
    // it('checks', () => {
    cy.get('app-info-panel')
    cy.get('app-info-panel').get('.app-name')
});
