import { NgZone } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ValidationHandler } from './token-validation/validation-handler';
import { UrlHelperService } from './url-helper.service';
import { OAuthEvent } from './events';
import { OAuthLogger, OAuthStorage, LoginOptions, ParsedIdToken } from './types';
import { AuthConfig } from './auth.config';
/**
 * Service for logging in and logging out with
 * OIDC and OAuth2. Supports implicit flow and
 * password flow.
 */
export declare class OAuthService extends AuthConfig {
    private ngZone;
    private http;
    private config;
    private urlHelper;
    private logger;
    /**
     * The ValidationHandler used to validate received
     * id_tokens.
     */
    tokenValidationHandler: ValidationHandler;
    /**
     * @internal
     * Deprecated:  use property events instead
     */
    discoveryDocumentLoaded: boolean;
    /**
     * @internal
     * Deprecated:  use property events instead
     */
    discoveryDocumentLoaded$: Observable<object>;
    /**
     * Informs about events, like token_received or token_expires.
     * See the string enum EventType for a full list of event types.
     */
    events: Observable<OAuthEvent>;
    /**
     * The received (passed around) state, when logging
     * in with implicit flow.
     */
    state?: string;
    private eventsSubject;
    private discoveryDocumentLoadedSubject;
    private silentRefreshPostMessageEventListener;
    private grantTypesSupported;
    private _storage;
    private accessTokenTimeoutSubscription;
    private idTokenTimeoutSubscription;
    private sessionCheckEventListener;
    private jwksUri;
    private sessionCheckTimer;
    private silentRefreshSubject;
    private inImplicitFlow;
    constructor(ngZone: NgZone, http: HttpClient, storage: OAuthStorage, tokenValidationHandler: ValidationHandler, config: AuthConfig, urlHelper: UrlHelperService, logger: OAuthLogger);
    /**
     * Use this method to configure the service
     * @param config the configuration
     */
    configure(config: AuthConfig): void;
    private configChanged();
    restartSessionChecksIfStillLoggedIn(): void;
    private restartRefreshTimerIfStillLoggedIn();
    private setupSessionCheck();
    /**
     * Will setup up silent refreshing for when the token is
     * about to expire.
     * @param params Additional parameter to pass
     */
    setupAutomaticSilentRefresh(params?: object): void;
    /**
     * Convenience method that first calls `loadDiscoveryDocument(...)` and
     * directly chains using the `then(...)` part of the promise to call
     * the `tryLogin(...)` method.
     *
     * @param options LoginOptions to pass through to `tryLogin(...)`
     */
    loadDiscoveryDocumentAndTryLogin(options?: LoginOptions): Promise<boolean>;
    /**
     * Convenience method that first calls `loadDiscoveryDocumentAndTryLogin(...)`
     * and if then chains to `initImplicitFlow()`, but only if there is no valid
     * IdToken or no valid AccessToken.
     *
     * @param options LoginOptions to pass through to `tryLogin(...)`
     */
    loadDiscoveryDocumentAndLogin(options?: LoginOptions): Promise<boolean>;
    private debug(...args);
    private validateUrlFromDiscoveryDocument(url);
    private validateUrlForHttps(url);
    private validateUrlAgainstIssuer(url);
    private setupRefreshTimer();
    private setupExpirationTimers();
    private setupAccessTokenTimer();
    private setupIdTokenTimer();
    private clearAccessTokenTimer();
    private clearIdTokenTimer();
    private calcTimeout(storedAt, expiration);
    /**
     * DEPRECATED. Use a provider for OAuthStorage instead:
     *
     * { provide: OAuthStorage, useValue: localStorage }
     *
     * Sets a custom storage used to store the received
     * tokens on client side. By default, the browser's
     * sessionStorage is used.
     * @ignore
     *
     * @param storage
     */
    setStorage(storage: OAuthStorage): void;
    /**
     * Loads the discovery document to configure most
     * properties of this service. The url of the discovery
     * document is infered from the issuer's url according
     * to the OpenId Connect spec. To use another url you
     * can pass it to to optional parameter fullUrl.
     *
     * @param fullUrl
     */
    loadDiscoveryDocument(fullUrl?: string): Promise<object>;
    private loadJwks();
    private validateDiscoveryDocument(doc);
    /**
     * Uses password flow to exchange userName and password for an
     * access_token. After receiving the access_token, this method
     * uses it to query the userinfo endpoint in order to get information
     * about the user in question.
     *
     * When using this, make sure that the property oidc is set to false.
     * Otherwise stricter validations take place that make this operation
     * fail.
     *
     * @param userName
     * @param password
     * @param headers Optional additional http-headers.
     */
    fetchTokenUsingPasswordFlowAndLoadUserProfile(userName: string, password: string, headers?: HttpHeaders): Promise<object>;
    /**
     * Loads the user profile by accessing the user info endpoint defined by OpenId Connect.
     *
     * When using this with OAuth2 password flow, make sure that the property oidc is set to false.
     * Otherwise stricter validations take place that make this operation fail.
     */
    loadUserProfile(): Promise<object>;
    /**
     * Uses password flow to exchange userName and password for an access_token.
     * @param userName
     * @param password
     * @param headers Optional additional http-headers.
     */
    fetchTokenUsingPasswordFlow(userName: string, password: string, headers?: HttpHeaders): Promise<object>;
    /**
     * Refreshes the token using a refresh_token.
     * This does not work for implicit flow, b/c
     * there is no refresh_token in this flow.
     * A solution for this is provided by the
     * method silentRefresh.
     */
    refreshToken(): Promise<object>;
    private removeSilentRefreshEventListener();
    private setupSilentRefreshEventListener();
    /**
     * Performs a silent refresh for implicit flow.
     * Use this method to get new tokens when/before
     * the existing tokens expire.
     */
    silentRefresh(params?: object, noPrompt?: boolean): Promise<OAuthEvent>;
    private canPerformSessionCheck();
    private setupSessionCheckEventListener();
    private handleSessionUnchanged();
    private handleSessionChange();
    private waitForSilentRefreshAfterSessionChange();
    private handleSessionError();
    private removeSessionCheckEventListener();
    private initSessionCheck();
    private startSessionCheckTimer();
    private stopSessionCheckTimer();
    private checkSession();
    private createLoginUrl(state?, loginHint?, customRedirectUri?, noPrompt?, params?);
    initImplicitFlowInternal(additionalState?: string, params?: string | object): void;
    /**
     * Starts the implicit flow and redirects to user to
     * the auth servers' login url.
     *
     * @param additionalState Optional state that is passed around.
     *  You'll find this state in the property `state` after `tryLogin` logged in the user.
     * @param params Hash with additional parameter. If it is a string, it is used for the
     *               parameter loginHint (for the sake of compatibility with former versions)
     */
    initImplicitFlow(additionalState?: string, params?: string | object): void;
    private callOnTokenReceivedIfExists(options);
    private storeAccessTokenResponse(accessToken, refreshToken, expiresIn, grantedScopes);
    /**
     * Checks whether there are tokens in the hash fragment
     * as a result of the implicit flow. These tokens are
     * parsed, validated and used to sign the user in to the
     * current client.
     *
     * @param options Optional options.
     */
    tryLogin(options?: LoginOptions): Promise<boolean>;
    private validateNonceForAccessToken(accessToken, nonceInState);
    protected storeIdToken(idToken: ParsedIdToken): void;
    protected storeSessionState(sessionState: string): void;
    protected getSessionState(): string;
    private handleLoginError(options, parts);
    /**
     * @ignore
     */
    processIdToken(idToken: string, accessToken: string): Promise<ParsedIdToken>;
    /**
     * Returns the received claims about the user.
     */
    getIdentityClaims(): object;
    /**
     * Returns the granted scopes from the server.
     */
    getGrantedScopes(): object;
    /**
     * Returns the current id_token.
     */
    getIdToken(): string;
    private padBase64(base64data);
    /**
     * Returns the current access_token.
     */
    getAccessToken(): string;
    getRefreshToken(): string;
    /**
     * Returns the expiration date of the access_token
     * as milliseconds since 1970.
     */
    getAccessTokenExpiration(): number;
    private getAccessTokenStoredAt();
    private getIdTokenStoredAt();
    /**
     * Returns the expiration date of the id_token
     * as milliseconds since 1970.
     */
    getIdTokenExpiration(): number;
    /**
     * Checkes, whether there is a valid access_token.
     */
    hasValidAccessToken(): boolean;
    /**
     * Checks whether there is a valid id_token.
     */
    hasValidIdToken(): boolean;
    /**
     * Returns the auth-header that can be used
     * to transmit the access_token to a service
     */
    authorizationHeader(): string;
    /**
     * Removes all tokens and logs the user out.
     * If a logout url is configured, the user is
     * redirected to it.
     * @param noRedirectToLogoutUrl
     */
    logOut(noRedirectToLogoutUrl?: boolean): void;
    /**
     * @ignore
     */
    createAndSaveNonce(): Promise<string>;
    protected createNonce(): Promise<string>;
    private checkAtHash(params);
    private checkSignature(params);
}