package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ResponseType;

import cucumber.api.java.en.Then;

public class NIB04Contracts extends SupportSteps {
	private static final String RESPONSE_TYPE = "responseType";
	private static final String JWT_TOKEN = "jwtToken";
	private static final String CREDENTIAL = "credential";
	private static final String ACCOUNT_ID = "accountId";
	private static final String ACCOUNT_NAME = "accountName";
	private static final String DATA_SOURCE = "dataSource";
	private static final String CORPORATION_ID = "corporationId";
	private static final String ASSETS_COUNT = "assetsCount";
	private static final String WALLET_BALANCE = "walletBalance";
	private static final String MINING_RESOURCES_ESTIMATED_VALUE = "miningResourcesEstimatedValue";
	private static final String RACE_NAME = "raceName";
	private NeoComWorld neocomWorld;

	@Autowired
	public NIB04Contracts(
			final ConverterContainer cucumberTableToRequestConverters,
			final NeoComWorld neocomWorld ) {
		super( cucumberTableToRequestConverters );
		this.neocomWorld = neocomWorld;
	}

	@Then("the {string} has the next contents")
	public void the_has_the_next_contents( final String responseTypeName, final List<Map<String, String>> dataTable ) {
		final ResponseType responseType = ResponseType.from( responseTypeName );
		switch (responseType) {
			case VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME:
				final Map<String, String> row = dataTable.get( 0 );
				final ValidateAuthorizationTokenResponse response = this.neocomWorld.getValidateAuthorizationTokenResponse();
				Assert.assertEquals( row.get( RESPONSE_TYPE ), response.getResponseType() );
				Assert.assertEquals( row.get( JWT_TOKEN ), response.getJwtToken() );
				Assert.assertEquals( row.get( CREDENTIAL ), response.getCredential().getJsonClass() );
				Assert.assertEquals( Integer.valueOf( row.get( ACCOUNT_ID ) ).intValue(),
						response.getCredential().getAccountId() );
				Assert.assertEquals( Integer.valueOf( row.get( CORPORATION_ID ) ).intValue(),
						response.getCredential().getCorporationId() );
				Assert.assertEquals( row.get( ACCOUNT_NAME ), response.getCredential().getAccountName() );
				Assert.assertEquals( row.get( DATA_SOURCE ), response.getCredential().getDataSource() );
				Assert.assertEquals( Integer.valueOf( row.get( ASSETS_COUNT ) ).intValue(),
						response.getCredential().getAssetsCount() );
				Assert.assertEquals( Double.valueOf( row.get( WALLET_BALANCE ) ).doubleValue(),
						response.getCredential().getWalletBalance(), 1.0 );
				Assert.assertEquals( Double.valueOf( row.get( MINING_RESOURCES_ESTIMATED_VALUE ) ).doubleValue(),
						response.getCredential().getMiningResourcesEstimatedValue(), 1.0 );
				Assert.assertNull( row.get( RACE_NAME ) );
				break;
		}
	}
}