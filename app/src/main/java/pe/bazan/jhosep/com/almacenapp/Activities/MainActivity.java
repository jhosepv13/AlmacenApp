package pe.bazan.jhosep.com.almacenapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import pe.bazan.jhosep.com.almacenapp.Adapters.ProductosAdapter;
import pe.bazan.jhosep.com.almacenapp.Models.Producto;
import pe.bazan.jhosep.com.almacenapp.R;
import pe.bazan.jhosep.com.almacenapp.Services.ApiService;
import pe.bazan.jhosep.com.almacenapp.Services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView productosList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productosList = (RecyclerView) findViewById(R.id.recyclerview);
        productosList.setLayoutManager(new LinearLayoutManager(this));

        productosList.setAdapter(new ProductosAdapter());

        initialize();

    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Producto>> call = service.getProductos();

        //EJECUTAMOS LA LLAMADA Y RECEPCIONAMOS LOS DATOS EN EL CALLBACK
        call.enqueue(new Callback<List<Producto>>() {

            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Producto> productos = response.body();
                        Log.d(TAG, "productos: " + productos);

                        ProductosAdapter adapter = (ProductosAdapter) productosList.getAdapter();
                        adapter.setProductos(productos);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}