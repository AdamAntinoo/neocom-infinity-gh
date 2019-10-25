// - DOMAIN
import { NeoComException } from './NeoComException';

export class ExceptionCatalog {
    static UNDEFINED_INSTANCE = new NeoComException(
        {
            status: 1001,
            statusText: 'INTERNAL_EXCEPTION',
            allowsRetry: false,
            message: 'Trying to access an undefined application instance.'
        });
    static AUTHORIZATION_MISSING = new NeoComException(
        {
            status: 1002,
            statusText: 'INTERNAL_EXCEPTION',
            allowsRetry: false,
            message: 'The authorized credential is not present. Application requires new authentication.'
        });
    static JWT_TOKEN_INVALID = new NeoComException(
        {
            status: 1003,
            statusText: 'INTERNAL_EXCEPTION',
            allowsRetry: false,
            message: 'The authentication token is not valid or has been changed. Current credentials are invalid.'
        });
}
