# CatalogApi

All URIs are relative to *http://catalogo-service.apps.svc.cluster.local:8082*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getCatalogByProductId**](CatalogApi.md#getCatalogByProductId) | **GET** /api/catalog/{productId} | Obtener detalle e inventario de un producto |
| [**listCatalog**](CatalogApi.md#listCatalog) | **GET** /api/catalog | Listar los productos del catálogo (paginado) |



## getCatalogByProductId

> CatalogResponse getCatalogByProductId(productId)

Obtener detalle e inventario de un producto

### Example

```java
// Import classes:
import com.bootcamp.productos.client.catalog.ApiClient;
import com.bootcamp.productos.client.catalog.ApiException;
import com.bootcamp.productos.client.catalog.Configuration;
import com.bootcamp.productos.client.catalog.models.*;
import com.bootcamp.productos.client.catalog.api.CatalogApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://catalogo-service.apps.svc.cluster.local:8082");

        CatalogApi apiInstance = new CatalogApi(defaultClient);
        Long productId = 1L; // Long | Identificador del producto (entero positivo)
        try {
            CatalogResponse result = apiInstance.getCatalogByProductId(productId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CatalogApi#getCatalogByProductId");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **productId** | **Long**| Identificador del producto (entero positivo) | |

### Return type

[**CatalogResponse**](CatalogResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/problem+json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Producto encontrado en el catálogo |  -  |
| **400** | Parámetros inválidos (por ejemplo, productId menor a 1) |  -  |
| **404** | El producto no existe en el catálogo |  -  |


## listCatalog

> CatalogPage listCatalog(page, size)

Listar los productos del catálogo (paginado)

### Example

```java
// Import classes:
import com.bootcamp.productos.client.catalog.ApiClient;
import com.bootcamp.productos.client.catalog.ApiException;
import com.bootcamp.productos.client.catalog.Configuration;
import com.bootcamp.productos.client.catalog.models.*;
import com.bootcamp.productos.client.catalog.api.CatalogApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://catalogo-service.apps.svc.cluster.local:8082");

        CatalogApi apiInstance = new CatalogApi(defaultClient);
        Integer page = 0; // Integer | Número de página, empieza en 0
        Integer size = 20; // Integer | Elementos por página (máximo 100)
        try {
            CatalogPage result = apiInstance.listCatalog(page, size);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CatalogApi#listCatalog");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **page** | **Integer**| Número de página, empieza en 0 | [optional] [default to 0] |
| **size** | **Integer**| Elementos por página (máximo 100) | [optional] [default to 20] |

### Return type

[**CatalogPage**](CatalogPage.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/problem+json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Página de productos del catálogo |  -  |
| **400** | Parámetros inválidos (por ejemplo, productId menor a 1) |  -  |

