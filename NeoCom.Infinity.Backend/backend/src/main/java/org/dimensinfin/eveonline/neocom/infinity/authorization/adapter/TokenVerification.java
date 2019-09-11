package org.dimensinfin.eveonline.neocom.infinity.authorization.adapter;

import org.dimensinfin.eveonline.neocom.auth.TokenTranslationResponse;
import org.dimensinfin.eveonline.neocom.auth.VerifyCharacterResponse;

public class TokenVerification {
	private String authCode;
	private String dataSource;
	private TokenTranslationResponse tokenTranslationResponse;
	private String peck;
	private VerifyCharacterResponse verifyCharacterResponse;
	private Long accountIdentifier;

//	public TokenVerification(final String authCode) {
//		this.authCode = authCode;
//	}

	public String getAuthCode() {
		return authCode;
	}

	public String getDataSource() {
		return dataSource;
	}

	public TokenTranslationResponse getTokenTranslationResponse() {
		return tokenTranslationResponse;
	}

	public String getPeck() {
		return peck;
	}

	public VerifyCharacterResponse getVerifyCharacterResponse() {
		return verifyCharacterResponse;
	}

	public int getAccountIdentifier() {
		return this.accountIdentifier.intValue();
	}

	public TokenVerification setAuthCode(final String authCode) {
		this.authCode = authCode;
		return this;
	}

	public TokenVerification setDataSource(final String dataSource) {
		this.dataSource = dataSource;
		return this;
	}

	public TokenVerification setTokenTranslationResponse(TokenTranslationResponse tokenTranslationResponse) {
		this.tokenTranslationResponse = tokenTranslationResponse;
		return this;
	}

	public TokenVerification setPeck(String peck) {
		this.peck = peck;
		return this;
	}

	public TokenVerification setVerifyCharacterResponse(VerifyCharacterResponse verifyCharacterResponse) {
		this.verifyCharacterResponse = verifyCharacterResponse;
		return this;
	}

	public TokenVerification setAccountIdentifier(Long accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
		return this;
	}
}
