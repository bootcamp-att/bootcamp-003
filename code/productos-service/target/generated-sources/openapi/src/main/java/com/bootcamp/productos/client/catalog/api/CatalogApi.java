package com.bootcamp.productos.client.catalog.api;

import com.bootcamp.productos.client.catalog.ApiClient;

import com.bootcamp.productos.client.catalog.model.CatalogPage;
import com.bootcamp.productos.client.catalog.model.CatalogResponse;
import com.bootcamp.productos.client.catalog.model.Problem;

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

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-11-23T14:16:15.617476658-06:00[America/Mexico_City]")
public class CatalogApi {
    private ApiClient apiClient;

    public CatalogApi() {
        this(new ApiClient());
    }

    public CatalogApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Obtener detalle e inventario de un producto
     * 
     * <p><b>200</b> - Producto encontrado en el catálogo
     * <p><b>400</b> - Parámetros inválidos (por ejemplo, productId menor a 1)
     * <p><b>404</b> - El producto no existe en el catálogo
     * @param productId Identificador del producto (entero positivo) (required)
     * @return CatalogResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CatalogResponse getCatalogByProductId(Long productId) throws RestClientException {
        return getCatalogByProductIdWithHttpInfo(productId).getBody();
    }

    /**
     * Obtener detalle e inventario de un producto
     * 
     * <p><b>200</b> - Producto encontrado en el catálogo
     * <p><b>400</b> - Parámetros inválidos (por ejemplo, productId menor a 1)
     * <p><b>404</b> - El producto no existe en el catálogo
     * @param productId Identificador del producto (entero positivo) (required)
     * @return ResponseEntity&lt;CatalogResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CatalogResponse> getCatalogByProductIdWithHttpInfo(Long productId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'productId' is set
        if (productId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'productId' when calling getCatalogByProductId");
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

        ParameterizedTypeReference<CatalogResponse> localReturnType = new ParameterizedTypeReference<CatalogResponse>() {};
        return apiClient.invokeAPI("/api/catalog/{productId}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Listar los productos del catálogo (paginado)
     * 
     * <p><b>200</b> - Página de productos del catálogo
     * <p><b>400</b> - Parámetros inválidos (por ejemplo, productId menor a 1)
     * @param page Número de página, empieza en 0 (optional, default to 0)
     * @param size Elementos por página (máximo 100) (optional, default to 20)
     * @return CatalogPage
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CatalogPage listCatalog(Integer page, Integer size) throws RestClientException {
        return listCatalogWithHttpInfo(page, size).getBody();
    }

    /**
     * Listar los productos del catálogo (paginado)
     * 
     * <p><b>200</b> - Página de productos del catálogo
     * <p><b>400</b> - Parámetros inválidos (por ejemplo, productId menor a 1)
     * @param page Número de página, empieza en 0 (optional, default to 0)
     * @param size Elementos por página (máximo 100) (optional, default to 20)
     * @return ResponseEntity&lt;CatalogPage&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CatalogPage> listCatalogWithHttpInfo(Integer page, Integer size) throws RestClientException {
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

        ParameterizedTypeReference<CatalogPage> localReturnType = new ParameterizedTypeReference<CatalogPage>() {};
        return apiClient.invokeAPI("/api/catalog", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
