package org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NeoComApiv1 {
	@GET("/api/v1/neocom/validateAuthorizationToken/{code}/state/{state}/datasource/{datasource}")
	Call<ValidateAuthorizationTokenResponse> validateAuthorizationToken( @Header("Content-Type") String contentType,
	                                                                     @Path("code") String code,
	                                                                     @Path("state") String state,
	                                                                     @Path("datasource") String datasource );
}
