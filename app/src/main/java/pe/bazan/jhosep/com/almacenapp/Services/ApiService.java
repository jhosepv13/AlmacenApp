package pe.bazan.jhosep.com.almacenapp.Services;

import java.util.List;

import pe.bazan.jhosep.com.almacenapp.Models.Producto;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Alumno on 4/05/2018.
 */
public interface ApiService {

    String API_BASE_URL = "https://laravel-project-jhosepv13.c9users.io";

    @GET("v1/productos")
    Call<List<Producto>> getProductos();

}
