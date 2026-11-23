# DiscountApi

All URIs are relative to *http://descuento-service.apps.svc.cluster.local:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getDiscountByProductId**](DiscountApi.md#getDiscountByProductId) | **GET** /api/discount/{productId} | Obtener el descuento vigente de un producto |
| [**listDiscounts**](DiscountApi.md#listDiscounts) | **GET** /api/discount | Listar los descuentos vigentes (paginado) |



## getDiscountByProductId

> DiscountResponse getDiscountByProductId(productId)

Obtener el descuento vigente de un producto

404 significa \&quot;el producto no tiene descuento\&quot;. Quien consume esta API (productos-service) debe interpretarlo como 0% y no como un error. 

### Example

```java
// Import classes:
import com.bootcamp.productos.client.discount.ApiClient;
import com.bootcamp.productos.client.discount.ApiException;
import com.bootcamp.productos.client.discount.Configuration;
import com.bootcamp.productos.client.discount.models.*;
import com.bootcamp.productos.client.discount.api.DiscountApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://descuento-service.apps.svc.cluster.local:8081");

        DiscountApi apiInstance = new DiscountApi(defaultClient);
        Long productId = 1L; // Long | Identificador del producto (entero positivo)
        try {
            DiscountResponse result = apiInstance.getDiscountByProductId(productId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DiscountApi#getDiscountByProductId");
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

[**DiscountResponse**](DiscountResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/problem+json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Descuento encontrado |  -  |
| **400** | Parámetros inválidos (por ejemplo, productId menor a 1) |  -  |
| **404** | El producto no tiene descuento vigente |  -  |


## listDiscounts

> DiscountPage listDiscounts(page, size)

Listar los descuentos vigentes (paginado)

### Example

```java
// Import classes:
import com.bootcamp.productos.client.discount.ApiClient;
import com.bootcamp.productos.client.discount.ApiException;
import com.bootcamp.productos.client.discount.Configuration;
import com.bootcamp.productos.client.discount.models.*;
import com.bootcamp.productos.client.discount.api.DiscountApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://descuento-service.apps.svc.cluster.local:8081");

        DiscountApi apiInstance = new DiscountApi(defaultClient);
        Integer page = 0; // Integer | Número de página, empieza en 0
        Integer size = 20; // Integer | Elementos por página (máximo 100)
        try {
            DiscountPage result = apiInstance.listDiscounts(page, size);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DiscountApi#listDiscounts");
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

[**DiscountPage**](DiscountPage.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, application/problem+json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Página de descuentos |  -  |
| **400** | Parámetros inválidos (por ejemplo, productId menor a 1) |  -  |

