import React, { useState, useEffect } from 'react';

function App() {
  const [productos, setProductos] = useState([]);

  useEffect(() => {
    fetch('/productos')
      .then(response => response.json())
      .then(data => {
        setProductos(data);
      });
  }, []);

  return (
    <div>
      <h1>Lista de productos </h1>
      <ul>
        {productos.map(producto => (
          <li key={producto.id}>
            {producto.nombre} - {producto.precio}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {
private Connection conexion;

public BaseDatos() {
try {
conexion = DriverManager.getConnection("jdbc:mysql://localhost/NombreBaseDeDatos", "usuario", "contraseña");
} catch (SQLException e) {
e.printStackTrace();
}
}

public List<Producto> obtenerProductos() {
  List<Producto> productos = new ArrayList<>();

      try {
      PreparedStatement statement = conexion.prepareStatement("SELECT * FROM productos");
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
      Producto producto = new Producto();
      producto.setId(resultSet.getInt("id"));
      producto.setNombre(resultSet.getString("nombre"));
      producto.setPrecio(resultSet.getFloat("precio"));
      productos.add(producto);
      }
      } catch (SQLException e) {
      e.printStackTrace();
      }

      return productos;
      }
      }package com.example.demo;

public class Producto {
  private int id;
  private String nombre;
  private float precio;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public float getPrecio() {
    return precio;
  }

  public void setPrecio(float precio) {
    this.precio = precio;
  }
}
