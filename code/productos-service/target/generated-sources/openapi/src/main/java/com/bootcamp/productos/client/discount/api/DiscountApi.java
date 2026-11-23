package com.bootcamp.productos.client.discount.api;

import com.bootcamp.productos.client.discount.ApiClient;

import com.bootcamp.productos.client.discount.model.DiscountPage;
import com.bootcamp.productos.client.discount.model.DiscountResponse;
import com.bootcamp.productos.client.discount.model.Problem;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-11-23T14:16:15.526794068-06:00[America/Mexico_City]")
public class DiscountApi {
    private ApiClient apiClient;

    public DiscountApi() {
        this(new ApiClient());
    }

    public DiscountApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Obtener el descuento vigente de un producto
     * 404 significa \&quot;el producto no tiene descuento\&quot;. Quien consume esta API (productos-service) debe interpretarlo como 0% y no como un error. 
     * <p><b>200</b> - Descuento encontrado
     * <p><b>400</b> - Parámetros inválidos (por ejemplo, productId menor a 1)
     * <p><b>404</b> - El producto no tiene descuento vigente
     * @param productId Identificador del producto (entero positivo) (required)
     * @return DiscountResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DiscountResponse getDiscountByProductId(Long productId) throws RestClientException {
        return getDiscountByProductIdWithHttpInfo(productId).getBody();
    }

    /**
     * Obtener el descuento vigente de un producto
     * 404 significa \&quot;el producto no tiene descuento\&quot;. Quien consume esta API (productos-service) debe interpretarlo como 0% y no como un error. 
     * <p><b>200</b> - Descuento encontrado
     * <p><b>400</b> - Parámetros inválidos (por ejemplo, productId menor a 1)
     * <p><b>404</b> - El producto no tiene descuento vigente
     * @param productId Identificador del producto (entero positivo) (required)
     * @return ResponseEntity&lt;DiscountResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DiscountResponse> getDiscountByProductIdWithHttpInfo(Long productId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'productId' is set
        if (productId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'productId' when calling getDiscountByProductId");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("productId", productId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<DiscountResponse> localReturnType = new ParameterizedTypeReference<DiscountResponse>() {};
        return apiClient.invokeAPI("/api/discount/{productId}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Listar los descuentos vigentes (paginado)
     * 
     * <p><b>200</b> - Página de descuentos
     * <p><b>400</b> - Parámetros inválidos (por ejemplo, productId menor a 1)
     * @param page Número de página, empieza en 0 (optional, default to 0)
     * @param size Elementos por página (máximo 100) (optional, default to 20)
     * @return DiscountPage
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DiscountPage listDiscounts(Integer page, Integer size) throws RestClientException {
        return listDiscountsWithHttpInfo(page, size).getBody();
    }

    /**
     * Listar los descuentos vigentes (paginado)
     * 
     * <p><b>200</b> - Página de descuentos
     * <p><b>400</b> - Parámetros inválidos (por ejemplo, productId menor a 1)
     * @param page Número de página, empieza en 0 (optional, default to 0)
     * @param size Elementos por página (máximo 100) (optional, default to 20)
     * @return ResponseEntity&lt;DiscountPage&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DiscountPage> listDiscountsWithHttpInfo(Integer page, Integer size) throws RestClientException {
        Object localVarPostBody = null;
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "size", size));


        final String[] localVarAccepts = { 
            "application/json", "application/problem+json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<DiscountPage> localReturnType = new ParameterizedTypeReference<DiscountPage>() {};
        return apiClient.invokeAPI("/api/discount", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
