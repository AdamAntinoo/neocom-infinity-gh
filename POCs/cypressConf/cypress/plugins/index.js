const cypressTypeScriptPreprocessor = require('./cy-ts-preprocessor')

module.exports = (on, config) => {
    on("file:preprocessor", cypressTypeScriptPreprocessor);

    const targetEnv = config.env.TARGET_ENV || 'dev';

    const environmentConfig = require(`./config/${targetEnv}`);

    return {
        ...config,
        ...environmentConfig,
    };
}
module.exports = (on, config) => {
    on('file:preprocessor', cucumber());
    ...
};