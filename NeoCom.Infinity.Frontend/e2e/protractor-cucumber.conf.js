// @ts-check
// Protractor configuration file, see link for more information
// https://github.com/angular/protractor/blob/master/lib/config.ts

// const { SpecReporter } = require('jasmine-spec-reporter');
const path = require('path');

/**
 * @type { import("protractor").Config }
 */
exports.config = {
  allScriptsTimeout: 11000,
  specs: [
    './src/features/**/*.feature'
  ],
  capabilities: {
    'browserName': 'chrome'
  },
  directConnect: true,
  baseUrl: 'http://localhost:4200/',
  framework: 'custom',
  frameworkPath: require.resolve('protractor-cucumber-framework'),
  cucumberOpts: {
    require: ['./src/steps/**/*.steps.ts'],
    format: 'json:.tmp/results.json',
    strict: true
  },
  plugins: [{
    package: 'protractor-multiple-cucumber-html-reporter-plugin',
    options: {
      // read the options part
      automaticallyGenerateReport: true,
      removeExistingJsonReportFile: true
    }
  },
  {
    package: 'protractor-console-plugin',
    failOnWarning: false,
    failOnError: true,
    logWarnings: true,
    exclude: []
  }],
  onPrepare() {
    require('ts-node').register({
      project: require('path').join(__dirname, './tsconfig.json')
    });
  }
};
