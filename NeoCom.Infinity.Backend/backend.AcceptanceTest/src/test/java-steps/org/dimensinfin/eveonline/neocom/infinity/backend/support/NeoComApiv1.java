package org.dimensinfin.eveonline.neocom.infinity.backend.support;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.corporation.rest.v1.CorporationDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NeoComApiv1 {
	String NEOCOM_BACKEND_APP_HOST = "http://localhost:9500";

	@GET("/api/v1/neocom/validateAuthorizationToken/{code}/state/{state}/datasource/{datasource}")
	Call<ValidateAuthorizationTokenResponse> validateAuthorizationToken( @Header("Content-Type") String contentType,
	                                                                     @Path("code") String code,
	                                                                     @Path("state") String state,
	                                                                     @Path("datasource") String datasource );

	@GET("/api/v1/neocom/corporation/{corporationId}")
	Call<CorporationDataResponse> getCorporationData( @Header("Content-Type") String contentType,
	                                                  @Header ("Authorization")String authorization,
	                                                  @Path("corporationId") Integer code );
}
