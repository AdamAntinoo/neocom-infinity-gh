// protractor.config.debug.js
exports.config = {
    // The path to the seleniumServer executable instead of seleniumAddress.
    // Runs selenium server on execution start
    seleniumServerJar: './node_modules/selenium-standalone-jar/bin/selenium-server-standalone-3.0.1.jar',

    // Capabilities to be passed to the webdriver instance.
    capabilities: {
        browserName: 'chrome'
    },

    // Spec patterns are relative to the configuration file location passed
    // to protractor (in this example conf.js).
    specs: ['dist/e2e/**/*.e2e-spec.js'],
};
