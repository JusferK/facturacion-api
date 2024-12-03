package com.example.facturacion.service;

import com.example.facturacion.entity.Producto;
import com.example.facturacion.repository.ProductoRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/producto")
@CrossOrigin
public class ProductoServicio {

    @Autowired
    ProductoRepository productoRepository;

    @GetMapping("productos")
    public List<Producto> listaProductos() {
        return productoRepository.findAll();
    }

    @PostMapping("nuevo-producto")
    public Producto nuevoProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @PutMapping("editar-producto")
    public <T> Object editarProducto(@RequestBody Producto producto) {
        Optional<Producto> productoEncontrar = productoRepository.findById(producto.getProducto_id());

        if(productoEncontrar.isPresent()) {
            Producto productoEncontrado = productoEncontrar.get();
            productoEncontrado.setCosto(producto.getCosto());
            productoEncontrado.setDescripcion(producto.getDescripcion());
            productoEncontrado.setEstado(producto.getEstado());
            productoEncontrado.setPrecio_venta(producto.getPrecio_venta());
            return productoRepository.save(productoEncontrado);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("Error", "Producto no encontrado");
            response.put("Message", "Not Found");
            response.put("status", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("eliminar-producto/{producto_id}")
    public void eliminarProducto(@PathVariable Integer producto_id) {
        productoRepository.deleteById(producto_id);
    }

}
