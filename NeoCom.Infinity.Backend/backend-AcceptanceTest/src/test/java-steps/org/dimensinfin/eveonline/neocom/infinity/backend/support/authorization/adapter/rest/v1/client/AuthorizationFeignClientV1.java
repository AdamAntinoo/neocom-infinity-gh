package org.dimensinfin.eveonline.neocom.infinity.backend.support.authentication.adapter.converters;

@FeignClient(
        name = "cards-support",
        url = "${orangebank.client.cards.url}",
        primary = false,
        configuration = InternalFeignClientConfiguration.class)
public interface CardsFeignClientV1 {

    @PostMapping(path = "/v1/cards-agreements")
    ResponseEntity<CreateCardAgreementResponse> createCardAgreement(@RequestBody @Valid CreateCardAgreementRequest createCardAgreementRequest);
}
