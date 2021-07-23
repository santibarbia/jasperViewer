package com.example.empleados.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.empleados.model.Empleados;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleados, Long>{

    public List<Empleados> findAllById(Long Id);

    public Empleados findAllById(int i);


}
