/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.empleados.Services;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Win 10
 */
public class Conectar {
    static String bd = "empleados_prueba_system";
    static String login = "root";
    static String password = "1234";
    Connection connection = null;
    
    public Conectar(String url){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, login, password);
            if (connection != null) {
                System.out.println("Conexion a la bd "+ bd +" exitosa!");
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    public Connection getConnection(){
        return connection;
    }
    
    public void desconectar(){
        
        try {
            connection.close();
        } catch (Exception e) {
            
        }
    }
    
}
